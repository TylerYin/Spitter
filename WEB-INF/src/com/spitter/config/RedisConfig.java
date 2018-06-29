package com.spitter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.spitter.orm.domain.Product;
import com.spitter.redis.SmsMessageDelegateListener;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {
	
	@Autowired
	private SmsMessageDelegateListener smsListener;
	
	@Bean
	public RedisConnectionFactory redisCF() {
		JedisConnectionFactory cf = new JedisConnectionFactory();
		cf.setUsePool(true);
		cf.setPort(6379);
		cf.setPassword("");
		cf.setHostName("127.0.0.1");
		cf.setPoolConfig(jredisPoolConfig());
		return cf;
	}

	@Bean
	public JedisPoolConfig jredisPoolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		// 连接超时时是否阻塞，false时报异常,ture阻塞直到超时, 默认true
		jedisPoolConfig.setBlockWhenExhausted(true);
		// 逐出策略（默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)）
		jedisPoolConfig
				.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");

		// 最大空闲连接数, 默认8个
		jedisPoolConfig.setMaxIdle(8);

		// 最大连接数, 默认8个
		jedisPoolConfig.setMaxTotal(8);

		// 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,
		// 默认-1
		jedisPoolConfig.setMaxWaitMillis(-1);

		// 逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
		jedisPoolConfig.setMinEvictableIdleTimeMillis(1800000);

		// 最小空闲连接数, 默认0
		jedisPoolConfig.setMinIdle(0);

		// 每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
		jedisPoolConfig.setNumTestsPerEvictionRun(3);

		// 对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数
		// 时直接逐出,不再根据MinEvictableIdleTimeMillis判断 (默认逐出策略)
		jedisPoolConfig.setSoftMinEvictableIdleTimeMillis(1800000);

		// 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,
		// 默认-1
		jedisPoolConfig.setMaxWaitMillis(100);

		// 对拿到的connection进行validateObject校验
		jedisPoolConfig.setTestOnBorrow(true);

		// 在进行returnObject对返回的connection进行validateObject校验
		jedisPoolConfig.setTestOnReturn(true);

		// 定时对线程池中空闲的链接进行validateObject校验
		jedisPoolConfig.setTestWhileIdle(true);
		return jedisPoolConfig;
	}

	@Bean
	public RedisTemplate<String, Product> redisTemplate() {
		RedisTemplate<String, Product> redis = new RedisTemplate<>();
		redis.setConnectionFactory(redisCF());
		redis.setKeySerializer(new StringRedisSerializer());
		redis.setValueSerializer(new Jackson2JsonRedisSerializer<Product>(
				Product.class));
		return redis;
	}
	
	@Bean
	public MessageListenerAdapter listenerAdapter() {
		// 指定监听者和监听方法
		return new MessageListenerAdapter(smsListener, "onMessage");
	}
	
	@Bean
	public RedisMessageListenerContainer container() {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(redisCF());
		
		// 绑定监听者与信道的管理
		container.addMessageListener(listenerAdapter(), new PatternTopic("productQueue"));
		return container;
	}

}
