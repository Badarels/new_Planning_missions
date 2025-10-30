package com.businesscenterservices.notifications.listener;

import com.businesscenterservices.notifications.dto.MissionNotificationEvent;
import com.businesscenterservices.notifications.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MissionEventsListener {

    private final NotificationService notificationService;

    @KafkaListener(topics = "${kafka.topic.mission-events}",
            containerFactory = "missionEventKafkaListenerContainerFactory")
    public void onMissionEvent(MissionNotificationEvent event) {
        log.info("Réception d'un événement de mission pour {}", event.getRecipientEmail());
        notificationService.handleMissionNotification(event);
    }
}
