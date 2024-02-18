package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.validators.ValidatorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrackCommand implements Command {
    private static final String COMMAND_NAME = "/track";
    private static final String COMMAND_DESCRIPTION = "start tracking the link";

    private final ValidatorRepo linkValidator;

    @Autowired
    public TrackCommand(ValidatorRepo linkValidator) {
        this.linkValidator = linkValidator;
    }

    @Override
    public String command() {
        return COMMAND_NAME;
    }

    @Override
    public String description() {
        return COMMAND_DESCRIPTION;
    }

    @Override
    public SendMessage handle(Update update) {
        String messageText = update.message().text();
        String[] commandParts = messageText.split(" ");

        if (commandParts.length != 2) {
            return new SendMessage(update.message().chat().id(),
                "Please provide a link to track along with the /track command.");
        }

        String trackedLink = commandParts[1];
        String responseText;

        if (linkValidator.isBotSupportLink(trackedLink)) {
            responseText = "Started tracking the link: " + trackedLink;
        } else {
            responseText = "Please provide a correct supportable link";
        }

        //todo logic of tracking url (repository of tracking urls)

        return new SendMessage(update.message().chat().id(), responseText);
    }
}
