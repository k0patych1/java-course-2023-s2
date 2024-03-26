package edu.java.services.parsers;

import java.net.URI;
import org.springframework.stereotype.Service;

@Service
public class StackOverFlowUrlParser {
    public boolean isStackOverFlowUrl(String url) {
        URI uri = URI.create(url);
        return uri.getHost().equals("stackoverflow.com");
    }

    public String getQuestionId(String url) {
        URI uri = URI.create(url);
        return uri.getPath().split("/")[2];
    }
}
