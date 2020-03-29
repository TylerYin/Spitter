package com.spitter.activemq.alerts;

import javax.inject.Inject;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.spitter.orm.domain.Spitter;

/**
 * @author Tyler Yin
 */
public class AmqpJmsImpl implements Jms {

    @Inject
    private RabbitTemplate rabbit;

    @Override
    public void sendSpittleAlert(Spitter spitter) {
        rabbit.convertAndSend("spring-boot-exchange", "spring-boot-queue", spitter);
    }

    @Override
    public Spitter retrieveSpittleAlert() {
        return (Spitter) rabbit.receiveAndConvert();
    }
}
