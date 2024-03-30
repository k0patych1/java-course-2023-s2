package edu.java.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import edu.java.services.deserializer.OffsetDateTimeJsonDeserializer;
import java.time.OffsetDateTime;
import java.util.List;

public record StackOverFlowLastAnswer(@JsonProperty("items") List<Answer> answerList) {

    public record Answer(
        @JsonProperty("owner")
        Owner owner,

        @JsonProperty("creation_date")
        @JsonDeserialize(using = OffsetDateTimeJsonDeserializer.class)
        OffsetDateTime time,

        @JsonProperty("score")
        int score
    ) {
        public record Owner(
            @JsonProperty("display_name")
            String displayName
        ) {
        }
    }
}
