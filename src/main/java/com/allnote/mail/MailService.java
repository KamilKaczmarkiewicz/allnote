package com.allnote.mail;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class MailService {

    private final JavaMailSender javaMailSender;

    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String sender;

    public void sendMail(String subject, String recipient, Map<String, Object> contextMap, String templateName) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            Context context = new Context();
            context.setVariables(contextMap);
            String html = templateEngine.process(templateName, context);
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(recipient);
            helper.setSubject(subject);
            helper.setFrom(sender);
            helper.setText(html, true);
            new Thread(() -> {
                log.info("Sending mail to: " + recipient);
                sendMail(message);
            }).start();
        } catch (Exception e) {
            log.error(e);
        }
    }

    public void sendMail(MimeMessage message) {
        javaMailSender.send(message);
        log.info("Mail send successfully.");
    }
}
