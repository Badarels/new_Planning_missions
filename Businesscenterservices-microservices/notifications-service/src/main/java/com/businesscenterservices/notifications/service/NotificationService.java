package com.businesscenterservices.notifications.service;

import com.businesscenterservices.notifications.dto.MissionNotificationEvent;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public void handleMissionNotification(MissionNotificationEvent event) {
        if (event.isSendEmail()) {
            try {
                sendMissionEmail(event);
            } catch (MessagingException e) {
                log.error("Erreur lors de l'envoi de l'email de mission à {}", event.getRecipientEmail(), e);
            }
        }

        if (event.isSendSms()) {
            sendMissionSms(event);
        }
    }

    public void sendMissionEmail(MissionNotificationEvent event) throws MessagingException {
        Map<String, Object> variables = new HashMap<>();
        variables.put("medecinNom", event.getMedecinNom());
        variables.put("centreNom", event.getCentreNom());
        variables.put("detailMission", event.getDetailMission());
        variables.put("dateDebut", event.getDateDebut());
        variables.put("dateFin", event.getDateFin());
        variables.put("startTime", event.getStartTime());
        variables.put("endTime", event.getEndTime());
        variables.put("remuneration", event.getRemuneration());
        variables.put("statut", event.getStatut());

        Context context = new Context();
        variables.forEach(context::setVariable);
        String htmlContent = templateEngine.process("mission-notification", context);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(event.getRecipientEmail());
        helper.setSubject("Nouvelle mission - " + event.getCentreNom());
        helper.setText(htmlContent, true);

        mailSender.send(message);
        log.info("Email de mission envoyé à {}", event.getRecipientEmail());
    }

    private void sendMissionSms(MissionNotificationEvent event) {
        // TODO: implémenter l'appel réel à un fournisseur SMS (Twilio, etc.)
        log.info("SMS envoyé à {} pour mission '{}'", event.getRecipientPhone(), event.getDetailMission());
    }
}
