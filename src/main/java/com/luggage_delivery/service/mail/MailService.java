package com.luggage_delivery.service.mail;
/*
  User: admin
  Cur_date: 23.12.2022
  Cur_time: 15:11
*/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:mail-info.properties")
public class MailService {

    private final static Logger LOG = LoggerFactory.getLogger(MailService.class);

    private final JavaMailSender mailSender;

    @Value("${username}")
    private String fromUser;

    @Autowired
    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String toUser, String messageBody, String subject) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromUser);
        message.setTo(toUser);
        message.setText(messageBody);
        message.setSubject(subject);

        mailSender.send(message);
        LOG.debug("EMAIL WAS SUCCESSFULLY SEND TO " + toUser + " USER");
    }
}