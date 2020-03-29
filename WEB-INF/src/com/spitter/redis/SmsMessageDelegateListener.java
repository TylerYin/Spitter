package com.spitter.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import com.spitter.orm.domain.Product;

/**
 * @author Tyler Yin
 */
@Component
public class SmsMessageDelegateListener implements MessageListener {

    @Autowired
    private RedisTemplate<String, Product> redisTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        RedisSerializer<?> serializer = redisTemplate.getValueSerializer();
        Product product = (Product) serializer.deserialize(message.getBody());
        System.out.println("当前获取到的产品为编号: " + product.getNo() + " 的产品, 需要生码数量："
                + product.getNum() + ", 创建时间：" + product.getCreateDate());
    }
}