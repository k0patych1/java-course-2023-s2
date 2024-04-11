package edu.java.bot.services.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.LinkUpdate;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

public class LinkUpdateRequestSerializer implements Serializer<LinkUpdate> {

    private final ObjectMapper objectMapper;

    public LinkUpdateRequestSerializer() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public byte[] serialize(String topic, LinkUpdate data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Error during serialization: " + data, e);
        }
    }
}
