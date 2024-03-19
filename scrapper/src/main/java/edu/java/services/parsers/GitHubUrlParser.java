package edu.java.services.parsers;

import java.net.URI;
import org.springframework.stereotype.Service;

@Service
public class GitHubUrlParser {
    public boolean isGitHubUrl(URI url) {
        return url.getHost().equals("github.com");
    }

    public String getUserName(URI url) {
        return url.getPath().split("/")[1];
    }

    public String getRepositoryName(URI url) {
        return url.getPath().split("/")[2];
    }
}
