package com.movieflix.service;

import com.movieflix.dto.MailBody;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    public void sendMail(MailBody mailBody){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailBody.to());
        message.setFrom("");
        message.setSubject(mailBody.subject());
        message.setText(mailBody.text());

        mailSender.send(message);
    }
}
