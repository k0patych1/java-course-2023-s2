package edu.java.services.clients;

import edu.java.models.GitHubRepoLastUpdate;
import edu.java.models.dto.Link;

public interface IGitHubClient {
    GitHubRepoLastUpdate fetchRepo(Link link);

    String getInfoAboutUpdate(Link link);
}
