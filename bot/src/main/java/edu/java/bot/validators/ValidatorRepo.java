package edu.java.bot.validators;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidatorRepo {
    private final List<? extends LinkValidator> validators;

    @Autowired
    public ValidatorRepo(List<? extends LinkValidator> validators) {
        this.validators = validators;
    }

    public boolean isBotSupportLink(String link) {
        return validators.stream().anyMatch(linkValidator -> linkValidator.isLinkValid(link));
    }
}
