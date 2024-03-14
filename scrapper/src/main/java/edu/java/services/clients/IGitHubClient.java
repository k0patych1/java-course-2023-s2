package edu.java.services.clients;

import edu.java.models.GitHubRepoLastUpdate;

public interface IGitHubClient {
    GitHubRepoLastUpdate fetchRepo(String user, String repository);
}
