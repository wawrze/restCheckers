package com.wawrze.restcheckers.services;

import com.wawrze.restcheckers.config.AppInfoConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    AppInfoConfig appInfoConfig;
    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private JavaMailSender javaMailSender;

    void send(final String receiverEmail, final String subject, final String message) {
        LOGGER.info("Starting email preparation...");
        try {
            javaMailSender.send(createMimeMessage(receiverEmail, subject, message));
            LOGGER.info("Email has been sent.");
        } catch (MailException e) {
            LOGGER.error("Email sending failed: ", e.getMessage(), e);
        }
    }

    private MimeMessagePreparator createMimeMessage(final String receiverEmail, final String subject,
                                                    final String message) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(receiverEmail);
            messageHelper.setSubject(subject);
            messageHelper.setText(message, true);
            messageHelper.setFrom(appInfoConfig.getAppEmail(), "RestCheckers Application");
        };
    }

}