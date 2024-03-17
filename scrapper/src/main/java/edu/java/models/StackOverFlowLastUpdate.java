package edu.java.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import edu.java.services.deserializer.OffsetDateTimeJsonDeserializer;
import java.time.OffsetDateTime;
import java.util.List;

public record StackOverFlowLastUpdate(@JsonProperty("items") List<Answer> answerList) {

    public record Answer(
        @JsonProperty("last_activity_date")
        @JsonDeserialize(using = OffsetDateTimeJsonDeserializer.class)
        OffsetDateTime time
    ) {
    }
}

