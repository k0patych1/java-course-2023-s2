package edu.java.bot.services.serializer;

import model.LinkUpdate;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.Serializer;

public class CompositeUpdateSerializer implements Serializer<Object> {

    private final Serializer<LinkUpdate> linkUpdateRequestSerializer;
    private final ByteArraySerializer byteArraySerializer;

    public CompositeUpdateSerializer() {
        this.linkUpdateRequestSerializer = new LinkUpdateRequestSerializer();
        this.byteArraySerializer = new ByteArraySerializer();
    }

    @Override
    public byte[] serialize(String topic, Object data) {
        if (data instanceof LinkUpdate linkUpdate) {
            return linkUpdateRequestSerializer.serialize(topic, linkUpdate);
        }

        return byteArraySerializer.serialize(topic, (byte[]) data);
    }
}
