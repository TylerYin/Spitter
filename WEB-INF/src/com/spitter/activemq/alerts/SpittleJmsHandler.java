package com.spitter.activemq.alerts;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spitter.mail.SpitterMailService;
import com.spitter.orm.domain.Spitter;
import com.spitter.orm.domain.Spittle;

/**
 * 通过配置JMS监听器来实现消费者的功能有两种实现方式; 第一种方式是采用纯POJO的方式，在监听器中设置该POJO实例并指定处理方法。
 * 另外一种是POJO实现MessageListener接口，JMS监听器会自动调用onMessage方法，不会再调用在监听器中指定的方法。
 */

/**
 * @author Tyler Yin
 */
@Component
public class SpittleJmsHandler {// implements MessageListener{

    @Autowired
    private SpitterMailService mailService;

    public void handleSpittleJms(Spitter spitter) {
        try {
            sendSimpleSpittleEmail(spitter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleSpittleJms(String spitter) {
        System.out.println(spitter);
    }

    private void sendSimpleSpittleEmail(Spitter spitter) throws Exception {
        Spittle spittle = new Spittle(1L, spitter, "Hey, Welcome to register accout!", new Date());
        mailService.sendSimpleSpittleEmail(spittle);
    }

    // @Override
    // public void onMessage(Message arg0) {
    // System.out.println("================");
    // }
}
