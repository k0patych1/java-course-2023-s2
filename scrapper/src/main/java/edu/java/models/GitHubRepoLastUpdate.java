package edu.java.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record GitHubRepoLastUpdate(
    @JsonProperty("name")
     String name,

     @JsonProperty("updated_at")
     OffsetDateTime time
    ){
}
