package com.spitter.activemq.alerts;

import com.spitter.orm.domain.Spitter;

public interface ActiveMQJMS {

	void sendSpittleAlert(Spitter spitter);

	Spitter retrieveSpittleAlert();

}