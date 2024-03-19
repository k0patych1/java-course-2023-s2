package edu.java.services.parsers;

import java.net.URI;
import org.springframework.stereotype.Service;

@Service
public class GitHubUrlParser {
    public boolean isGitHubUrl(String url) {
        URI uri = URI.create(url);
        return uri.getHost().equals("github.com");
    }

    public String getUserName(String url) {
        URI uri = URI.create(url);
        return uri.getPath().split("/")[1];
    }

    public String getRepositoryName(String url) {
        URI uri = URI.create(url);
        return uri.getPath().split("/")[2];
    }
}
