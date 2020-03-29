package com.spitter.mail;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.spitter.orm.domain.Spittle;

/**
 * @author Tyler Yin
 */
@Component
public class SpitterMailServiceImpl implements SpitterMailService {

    private JavaMailSender mailSender;
    private VelocityEngine velocityEngine;

    @Autowired
    public SpitterMailServiceImpl(JavaMailSender mailSender, VelocityEngine velocityEngine) {
        this.mailSender = mailSender;
        this.velocityEngine = velocityEngine;
    }

    @Override
    public void sendSimpleSpittleEmail(Spittle spittle) throws MessagingException {
        Map<String, Object> model = new HashMap<>(2);
        model.put("spitterName", spittle.getSpitter().getUsername());
        model.put("spittleText", spittle.getMessage());
        String emailText = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                "com/spitter/mail/resource/emailTemplate.vm", "UTF-8", model);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        String spitterName = spittle.getSpitter().getUsername();
        helper.setFrom("yjfruby@126.com");
        helper.setTo(spittle.getSpitter().getEmail());
        helper.setSubject("New spittle from " + spitterName);
        helper.setText(emailText, true);
        ClassPathResource couponImage = new ClassPathResource("com/spitter/mail/resource/spitterLogo.png");
        helper.addInline("coupon", couponImage);
        mailSender.send(message);
    }
}
