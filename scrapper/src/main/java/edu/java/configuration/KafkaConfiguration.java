package edu.java.configuration;

import edu.java.models.dto.request.LinkUpdate;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
@RequiredArgsConstructor
public class KafkaConfiguration {
    private final ApplicationConfig applicationConfig;

    @Bean
    public ProducerFactory<Integer, LinkUpdate> producerFactory() {
        return new DefaultKafkaProducerFactory<>(senderProps());
    }

    @Bean
    public KafkaTemplate<Integer, LinkUpdate> kafkaTemplate(ProducerFactory<Integer, LinkUpdate> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    private Map<String, Object> senderProps() {
        Map<String, Object> props = new HashMap<>();
        ApplicationConfig.Kafka kafka = applicationConfig.kafka();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.bootstrapServers());
        props.put(ProducerConfig.LINGER_MS_CONFIG, kafka.linger());
        props.put(ProducerConfig.ACKS_CONFIG, kafka.acksMode());
        props.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, kafka.deliveryTimeout());
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, kafka.batchSize());
        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, kafka.maxInFlightPerConnection());
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, kafka.enableIdempotence());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }
}
