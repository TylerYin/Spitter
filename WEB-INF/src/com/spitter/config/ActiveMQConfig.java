package com.spitter.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.jms.remoting.JmsInvokerProxyFactoryBean;
import org.springframework.jms.remoting.JmsInvokerServiceExporter;

import com.spitter.activemq.alerts.JMS;
import com.spitter.activemq.alerts.ActiveMQJMSImpl;
import com.spitter.activemq.alerts.SpittleJMSHandler;

//@Configuration
public class ActiveMQConfig {

	/**
	 * ActiveMQ5.0版本默认启动时，启动了内置的jetty服务器，提供一个demo应用和用于监控ActiveMQ的admin应用。
	 * admin：http://127.0.0.1:8161/admin/ 用户名，密码都是 admin
	 * demo：http://127.0.0.1:8161/demo/
	 * 
	 * http://lavasoft.blog.51cto.com/62575/190811/
	 */

	@Autowired
	private SpittleJMSHandler spittleJMSHandler;

	@Bean
	public ActiveMQQueue spittleQueue() {
		ActiveMQQueue spittleQueue = new ActiveMQQueue();
		spittleQueue.setPhysicalName("spitter.alert.queue");
		return spittleQueue;
	}

	@Bean
	public ActiveMQTopic spittleTopic() {
		ActiveMQTopic spittleTopic = new ActiveMQTopic();
		spittleTopic.setPhysicalName("spitter.alert.topic");
		return spittleTopic;
	}

	@Bean
	public ActiveMQConnectionFactory connectionFactory() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setTrustAllPackages(true);
		connectionFactory.setBrokerURL("tcp://localhost:61616");
		return connectionFactory;
	}

	// @Bean
	// public MessageConverter messageConverter() {
	// return new MappingJackson2MessageConverter();
	// }

	/**
	 * 默认情况下，JmsTemplate在convertAndSend()方法中会使用SimpleMessageConverter。
	 * 通过声明MessageConverter的不同实现并将其注入到JmsTemplate的MessageConverter属性中，可以重写这种行为。
	 * 如上面所示，声明一個MappingJackson2MessageConverter的實現，採用JmsTemplate的set方法重写这种行为。
	 */
	@Bean
	public JmsTemplate jmsTemplate() {
		JmsTemplate jmsTemplate = new JmsTemplate();
		jmsTemplate.setConnectionFactory(connectionFactory());
		jmsTemplate.setDefaultDestinationName("spitter.alert.queue");
		return jmsTemplate;
	}

	@Bean(name = "activeMQJMS")
	public ActiveMQJMSImpl activeMQJMS() {
		return new ActiveMQJMSImpl();
	}

	/**
	 * 默认情况下，JmsTemplate在convertAndSend()方法中会使用SimpleMessageConverter。
	 * 通过声明MessageConverter的不同实现并将其注入到JmsTemplate的MessageConverter属性中，可以重写这种行为。
	 * 如上面所示，声明一個MappingJackson2MessageConverter的實現，採用JmsTemplate的set方法重写这种行为。
	 */
	@Bean
	public MessageListenerAdapter messageListenerAdapter() {
		MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter();
		messageListenerAdapter.setDelegate(spittleJMSHandler);
		messageListenerAdapter.setDefaultListenerMethod("handleSpittleJMS");
		return messageListenerAdapter;
	}

	@Bean
	public DefaultMessageListenerContainer messageListenerContainer() {
		DefaultMessageListenerContainer messageListenerContainer = new DefaultMessageListenerContainer();
		messageListenerContainer.setConnectionFactory(connectionFactory());
		messageListenerContainer.setDestinationName("spitter.alert.queue");
		messageListenerContainer.setMessageListener(messageListenerAdapter());
		messageListenerContainer.setAutoStartup(true);
		return messageListenerContainer;
	}

	// @Bean
	public JmsInvokerServiceExporter alertServiceExporter() {
		JmsInvokerServiceExporter jmsInvokerServiceExporter = new JmsInvokerServiceExporter();
		jmsInvokerServiceExporter.setService(activeMQJMS());
		jmsInvokerServiceExporter.setServiceInterface(JMS.class);
		return jmsInvokerServiceExporter;
	}

	// @Bean
	public JmsInvokerProxyFactoryBean jmsInvokerProxyFactoryBean() {
		JmsInvokerProxyFactoryBean jmsInvokerProxyFactoryBean = new JmsInvokerProxyFactoryBean();
		jmsInvokerProxyFactoryBean.setConnectionFactory(connectionFactory());
		jmsInvokerProxyFactoryBean.setQueueName("spitter.alert.queue");
		jmsInvokerProxyFactoryBean.setServiceInterface(JMS.class);
		return jmsInvokerProxyFactoryBean;
	}
}
