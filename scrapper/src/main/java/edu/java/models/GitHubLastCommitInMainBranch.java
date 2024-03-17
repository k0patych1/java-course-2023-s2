package edu.java.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

public record GitHubLastCommitInMainBranch(
    @JsonProperty("commit")
    Commit commit,

    @JsonProperty("stats")
    Stats stats
) {
    public record Commit(
        @JsonProperty("message")
        String message,

        @JsonProperty("committer")
        Committer committer
    ) {
        public record Committer(
            @JsonProperty("name")
            String name,

            @JsonProperty("date")
            OffsetDateTime time
        ) {
        }
    }

    public record Stats(
        @JsonProperty("additions")
        int additions,

        @JsonProperty("deletions")
        int deletions
    ) {
    }
}
