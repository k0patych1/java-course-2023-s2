package edu.java.bot.services.validators;

import org.springframework.stereotype.Service;

@Service
public class StackOverFlowValidator implements LinkValidator {
    @Override
    public boolean isLinkValid(String link) {
        return link.startsWith("http://stackoverflow.com") || link.startsWith("https://stackoverflow.com");
    }
}
