package com.spitter.mail;

import javax.mail.MessagingException;

import com.spitter.orm.domain.Spittle;

public interface SpitterMailService {

	public abstract void sendSimpleSpittleEmail(Spittle spittle) throws MessagingException;

}