package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.models.dto.request.AddLinkRequest;
import edu.java.bot.services.clients.IScrapperClient;
import edu.java.bot.services.validators.ValidatorRepo;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrackCommand implements Command {
    private static final String COMMAND_NAME = "/track";
    private static final String COMMAND_DESCRIPTION = "start tracking the link";

    private final ValidatorRepo linkValidator;

    private final IScrapperClient scrapperClient;

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

        AddLinkRequest addLinkRequest = new AddLinkRequest();
        addLinkRequest.setLink(URI.create(trackedLink));

        scrapperClient.addLink(update.message().chat().id(), addLinkRequest);

        return new SendMessage(update.message().chat().id(), responseText);
    }
}
