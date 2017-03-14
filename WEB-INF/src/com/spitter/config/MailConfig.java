package com.spitter.config;

import java.util.Properties;

import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

@Configuration
@PropertySource("classpath:com/spitter/mail/resource/mail.properties")
public class MailConfig {

	@Bean
	public JavaMailSenderImpl mailSender(Environment env) {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(env.getProperty("mailserver.host"));
		mailSender.setPort(Integer.parseInt(env.getProperty("mailserver.port")));
		mailSender.setUsername(env.getProperty("mailserver.username"));
		mailSender.setPassword(env.getProperty("mailserver.password"));
		return mailSender;
	}

	@Bean
	public VelocityEngineFactoryBean velocityEngine() {
		VelocityEngineFactoryBean velocityEngine = new VelocityEngineFactoryBean();
		Properties props = new Properties();
		props.setProperty("resource.loader", "class");
		props.setProperty("class.resource.loader.class", ClasspathResourceLoader.class.getName());
		velocityEngine.setVelocityProperties(props);
		return velocityEngine;
	}
}
