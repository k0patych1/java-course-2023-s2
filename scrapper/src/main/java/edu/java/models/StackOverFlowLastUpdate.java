package edu.java.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.io.IOException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

public record StackOverFlowLastUpdate(@JsonProperty("items") List<Answer> answerList) {

    public record Answer(
        @JsonProperty("last_activity_date")
        @JsonDeserialize(using = CustomDeserializer.class)
        OffsetDateTime time
    ) {
    }

    public static class CustomDeserializer extends JsonDeserializer<OffsetDateTime> {
        @Override
        public OffsetDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            long seconds = p.getLongValue();
            Instant instant = Instant.ofEpochSecond(seconds);
            return instant.atOffset(ZoneOffset.UTC);
        }
    }
}

