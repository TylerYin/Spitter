package com.spitter.activemq.alerts;

import javax.inject.Inject;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.spitter.orm.domain.Spitter;

public class AMQPJMSImpl implements JMS {

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
