package com.spitter.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.spitter.activemq.alerts.AmqpJmsImpl;
import com.spitter.activemq.alerts.SpittleJmsHandler;

/**
 * @author Tyler Yin
 */
@Configuration
public class AmqpConfig {

    /**
     * RabbitMQ是一个流行的开源消息代理，它实现了AMQP。
     * 默认情况下，连接工厂会假设RabbitMQ服务器监听localhost的5672端口，并且用户名和密码均为guest。
     * 在JMS中，队列和主题的路由行为都是通过规范建立的，AMQP与之不同，它的路由更加丰富和灵活，依赖于如何定义队列和Exchange以及如何将它们绑定在一起。
     * 配置参考：http://www.cnblogs.com/xingzc/p/5945030.html
     * <p>
     * AMQP管理主页 http://localhost:15672/
     * <p>
     * 若要在项目中同时启用RabbitMQ & ActiveMQ ，要解决Broker端口冲突问题,主要是 Broker都占了 61613 端口。
     * <p>
     * 程序运行之前，先要通过RabbitMQ控制台命令创建必须的Queue，broker并进行相应的绑定。
     */

    public static final String EXCHANGE = "spring-boot-exchange";
    public static final String ROUTING_KEY = "spring-boot-routingKey";

    @Autowired
    private SpittleJmsHandler spittleJmsHandler;

    @Bean(name = "amqpJmsImpl")
    public AmqpJmsImpl activeMqJms() {
        return new AmqpJmsImpl();
    }

    @Bean
    public DirectExchange defaultExchange() {
        return new DirectExchange(EXCHANGE);
    }

    /**
     * 队列持久
     *
     * @return
     */
    @Bean
    public Queue queue() {
        return new Queue("spring-boot-queue", true);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(defaultExchange()).with(ROUTING_KEY);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        connectionFactory.setVirtualHost("/");
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setConnectionFactory(connectionFactory());
        return rabbitTemplate;
    }

    @Bean
    public MessageListenerAdapter messageListenerAdapter() {
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter();
        messageListenerAdapter.setDelegate(spittleJmsHandler);
        messageListenerAdapter.setDefaultListenerMethod("handleSpittleJms");
        return messageListenerAdapter;
    }

    @Bean
    public SimpleMessageListenerContainer messageListenerContainer() {
        SimpleMessageListenerContainer messageListenerContainer = new SimpleMessageListenerContainer();
        messageListenerContainer.setConnectionFactory(connectionFactory());
        messageListenerContainer.setMessageListener(messageListenerAdapter());
        messageListenerContainer.setQueues(queue());
        messageListenerContainer.setExposeListenerChannel(true);
        messageListenerContainer.setAcknowledgeMode(AcknowledgeMode.AUTO);
        messageListenerContainer.setConcurrentConsumers(3);
        messageListenerContainer.setAutoStartup(true);
        return messageListenerContainer;
    }
}
