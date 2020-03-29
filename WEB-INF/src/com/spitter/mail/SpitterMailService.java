package com.spitter.mail;

import javax.mail.MessagingException;

import com.spitter.orm.domain.Spittle;

/**
 * @author Tyler Yin
 */
public interface SpitterMailService {

    /**
     * @param spittle
     * @throws MessagingException
     */
    void sendSimpleSpittleEmail(Spittle spittle) throws MessagingException;
}