package edu.java.services.clients;

import edu.java.models.GitHubRepoLastUpdate;

public interface GitHubClient {
    GitHubRepoLastUpdate fetchUser(String user, String repository);
}
