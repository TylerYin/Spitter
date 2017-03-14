package com.spitter.activemq.alerts;

import javax.inject.Inject;

import org.springframework.jms.core.JmsOperations;

import com.spitter.orm.domain.Spitter;

public class ActiveMQJMSImpl implements ActiveMQJMS {

	@Inject
	private JmsOperations jmsOperations;

	// public void sendSpittleAlert(final Spittle spittle) {
	// jmsOperations.send(
	// "spittle.alert.queue",
	// new MessageCreator() {
	// public Message createMessage(Session session)
	// throws JMSException {
	// return session.createObjectMessage(spittle);
	// }
	// }
	// );
	// }

	/*
	 * public void sendSpittleAlert(final Spittle spittle) { jmsOperations.send(
	 * new MessageCreator() { public Message createMessage(Session session)
	 * throws JMSException { return session.createObjectMessage(spittle); } } );
	 * }
	 */

	public void sendSpittleAlert(Spitter spitter) {
		jmsOperations.convertAndSend(spitter);
	}

	// public Spittle getSpittleAlert() {
	// try {
	// ObjectMessage message = (ObjectMessage) jmsOperations.receive();
	// return (Spittle) message.getObject();
	// } catch (JMSException e) {
	// throw JmsUtils.convertJmsAccessException(e);
	// }
	// }

	public Spitter retrieveSpittleAlert() {
		return (Spitter) jmsOperations.receiveAndConvert();
	}

	public void setJmsOperations(JmsOperations jmsOperations) {
		this.jmsOperations = jmsOperations;
	}
}
