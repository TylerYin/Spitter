package com.spitter.activemq.alerts;

import com.spitter.orm.domain.Spitter;

public interface JMS {

	void sendSpittleAlert(Spitter spitter);

	Spitter retrieveSpittleAlert();

}