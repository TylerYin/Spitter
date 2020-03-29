package com.spitter.activemq.alerts;

import com.spitter.orm.domain.Spitter;

/**
 * @author Tyler Yin
 */
public interface Jms {

	void sendSpittleAlert(Spitter spitter);

	Spitter retrieveSpittleAlert();
}