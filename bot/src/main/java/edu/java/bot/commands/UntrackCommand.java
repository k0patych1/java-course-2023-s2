package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.models.dto.request.RemoveLinkRequest;
import edu.java.bot.services.clients.IScrapperClient;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UntrackCommand implements Command {
    private static final String COMMAND_DESCRIPTION = "stop tracking the link";
    private static final String COMMAND_NAME = "/untrack";

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
                "Please provide a link to untrack along with the /untrack command.");
        }

        RemoveLinkRequest removeLinkRequest = new RemoveLinkRequest();
        removeLinkRequest.setLink(URI.create(commandParts[1]));

        scrapperClient.removeLink(update.message().chat().id(), removeLinkRequest);

        String responseText = "The link was successfully removed from tracking";

        return new SendMessage(update.message().chat().id(), responseText);
    }
}
