package com.spitter.redis;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.spitter.orm.domain.Product;

@Component
public class RedisSendMessageService {

	@Autowired
	private RedisConnectionFactory redisCF;

	@Autowired
	private RedisTemplate<String, Product> redisTemplate;

	private SimpleDateFormat SDF_WITH_DELIMITER = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/**
	 * Redis中写入字符串
	 */
	public void insertStringIntoRedis() {
		RedisConnection conn = redisCF.getConnection();
		String randomString = RandomStringUtils.randomAlphabetic(4);
		conn.set(randomString.getBytes(),
				(randomString + " Hello World").getBytes());
	}

	/**
	 * 点对点模式
	 * 
	 * Redis中写入对象
	 */
	public void writeProductIntoRedisByP2P(String cart, Product product) {
		redisTemplate.opsForList().rightPush(cart, product);
	}

	/**
	 * 点对点模式
	 * 
	 * Redis中读取对象
	 */
	public void readProductFromRedis(String cart) {
		Product product = redisTemplate.opsForList().leftPop(cart);
		if (null != product) {
			System.out.println("当前获取到的产品为编号 " + product.getNo() + " 的产品");
		} else {
			System.out.println("redis is empty");
		}
	}

	/**
	 * 订阅-发布模式
	 * 
	 * Redis中写入对象。 在SmsMessageDelegateListener中实现Redis中对象的读取操作
	 */
	public void writeProductIntoRedisBySB(String topic, Product product) {
		redisTemplate.convertAndSend(topic, product);
	}

	/**
	 * 生成Product
	 * 
	 * @return
	 */
	public Product generateProduct() {
		Product product = new Product();
		product.setNo(RandomStringUtils.randomNumeric(6));
		product.setName(RandomStringUtils.randomAlphabetic(6));
		product.setNum(50000);
		product.setCreateDate(SDF_WITH_DELIMITER.format(new Date()));
		product.setManufacture("臻诚科技");
		return product;
	}

}
