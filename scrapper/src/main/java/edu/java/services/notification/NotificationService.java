package edu.java.services.notification;

import edu.java.configuration.ApplicationConfig;
import edu.java.models.dto.request.LinkUpdate;
import edu.java.services.clients.IBotClient;
import edu.java.services.producer.ScrapperQueueProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final ApplicationConfig applicationConfig;

    private final ScrapperQueueProducer queueProducer;

    private final IBotClient botClient;

    public void update(LinkUpdate updateRequest) {
        if (applicationConfig.useQueue()) {
            queueProducer.update(updateRequest);
        } else {
            botClient.update(updateRequest);
        }
    }
}
