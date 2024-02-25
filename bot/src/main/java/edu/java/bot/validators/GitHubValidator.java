package edu.java.bot.validators;

import org.springframework.stereotype.Service;

@Service
public class GitHubValidator implements LinkValidator {
    @Override
    public boolean isLinkValid(String link) {
        return link.startsWith("https://github.com") || link.startsWith("http://github.com");
    }
}
