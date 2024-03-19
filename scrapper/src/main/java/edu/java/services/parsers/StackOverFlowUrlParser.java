package edu.java.services.parsers;

import java.net.URI;
import org.springframework.stereotype.Service;

@Service
public class StackOverFlowUrlParser {
    public String getQuestionId(URI url) {
        return url.getPath().split("/")[2];
    }
}
