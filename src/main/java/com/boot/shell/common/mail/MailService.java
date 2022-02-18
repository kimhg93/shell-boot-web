package com.boot.shell.common.mail;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailService {

    private JavaMailSender javaMailSender;
    private static final String FROM_ADDRESS = "";

    public void mailSend(Mail mail){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail.getMailTo());
        message.setSubject(mail.getMailSubject());
        message.setText(mail.getMailContent());
        message.setFrom(FROM_ADDRESS);
        javaMailSender.send(message);
    }
}

