package edu.java.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record GitHubRepoLastUpdate(
    @JsonProperty("name")
     String name,

     @JsonProperty("pushed_at")
     OffsetDateTime time
    ){
}
