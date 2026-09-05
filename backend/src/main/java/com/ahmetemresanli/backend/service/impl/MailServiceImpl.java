package com.ahmetemresanli.backend.service.impl;

import com.ahmetemresanli.backend.service.IMailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.ObjectProvider;

@Service
public class MailServiceImpl implements IMailService {
    private static final Logger log = LoggerFactory.getLogger(MailServiceImpl.class);
    private final JavaMailSender mailSender;
    private final boolean enabled;
    private final String from;

    public MailServiceImpl(ObjectProvider<JavaMailSender> mailSender,
                           @Value("${app.mail.enabled:false}") boolean enabled,
                           @Value("${spring.mail.username:}") String from) {
        this.mailSender = mailSender.getIfAvailable();
        this.enabled = enabled;
        this.from = from;
    }

    @Override
    public void send(String to, String subject, String body) {
        if (!enabled || mailSender == null) {
            log.info("Mail delivery is disabled; skipped message '{}' to {}", subject, to);
            return;
        }
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            if (from != null && !from.isBlank()) message.setFrom(from);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
        } catch (RuntimeException ex) {
            log.error("Mail delivery failed for subject '{}' to {}", subject, to, ex);
        }
    }
}
