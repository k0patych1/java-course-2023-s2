package edu.java.services.producer;

import edu.java.configuration.ApplicationConfig;
import edu.java.models.dto.request.LinkUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScrapperQueueProducer {
    private final ApplicationConfig applicationConfig;

    private final KafkaTemplate<Integer, LinkUpdate> kafkaTemplate;

    public void update(LinkUpdate update) {
        kafkaTemplate.send(applicationConfig.kafka().topic(), update);
    }
}
