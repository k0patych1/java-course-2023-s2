package edu.java.bot.configuration;

import edu.java.bot.services.serializer.CompositeUpdateSerializer;
import jakarta.validation.ConstraintViolationException;
import jakarta.xml.bind.ValidationException;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;

@Configuration
@RequiredArgsConstructor
public class KafkaDLQConfiguration {
    private final ApplicationConfig applicationConfig;

    @Bean
    public ProducerFactory<Integer, byte[]> producerFactory() {
        return new DefaultKafkaProducerFactory<>(senderDLQProps());
    }

    @Bean
    public KafkaTemplate<Integer, byte[]> kafkaTemplate(ProducerFactory<Integer, byte[]> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public DeadLetterPublishingRecoverer deadLetterPublishingRecoverer(KafkaTemplate<Integer, byte[]> template) {
        return new DeadLetterPublishingRecoverer(
            template,
            (cr, exception) -> new TopicPartition(applicationConfig.kafka().dlq().topic(), cr.partition())
        );
    }

    @Bean
    public CommonErrorHandler errorHandler(DeadLetterPublishingRecoverer recoverer) {
        var errorHandler = new DefaultErrorHandler(recoverer);
        errorHandler.addNotRetryableExceptions(ValidationException.class, ConstraintViolationException.class);
        return errorHandler;
    }

    private Map<String, Object> senderDLQProps() {
        Map<String, Object> props = new HashMap<>();

        ApplicationConfig.Kafka kafka = applicationConfig.kafka();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.bootstrapServers());
        props.put(ProducerConfig.LINGER_MS_CONFIG, kafka.dlq().linger());
        props.put(ProducerConfig.ACKS_CONFIG, kafka.dlq().acksMode());
        props.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, kafka.dlq().deliveryTimeout());
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, kafka.dlq().batchSize());
        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, kafka.dlq().maxInFlightPerConnection());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CompositeUpdateSerializer.class);

        return props;
    }
}
