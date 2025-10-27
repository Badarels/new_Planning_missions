package com.businesscenterservices.businesscenterservices.services;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public void sendEmail(String to, String subject, String templateName, Map<String, Object> variables) throws MessagingException {
        Context context = new Context();
        variables.forEach(context::setVariable);

        String htmlContent = templateEngine.process(templateName, context);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }

    public void sendPasswordResetEmail(String to, String resetToken) throws MessagingException {
        Map<String, Object> variables = Map.of(
            "resetToken", resetToken,
            "resetLink", "http://localhost:4200/reset-password?token=" + resetToken
        );

        sendEmail(
            to,
            "Réinitialisation de votre mot de passe",
            "reset-password-email",
            variables
        );
    }

    public void sendTwoFactorCode(String to, String code) throws MessagingException {
        Map<String, Object> variables = Map.of(
            "code", code,
            "validityMinutes", "5"
        );

        sendEmail(
            to,
            "Votre code d'authentification à deux facteurs",
            "two-factor-email",
            variables
        );
    }
} 