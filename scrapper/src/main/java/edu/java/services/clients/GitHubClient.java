package edu.java.services.clients;

import edu.java.models.GitHubRepoLastUpdate;

public interface GitHubClient {
    GitHubRepoLastUpdate fetchRepo(String user, String repository);
}
