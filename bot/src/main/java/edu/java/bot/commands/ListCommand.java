package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.models.dto.response.LinkResponse;
import edu.java.bot.models.dto.response.ListLinksResponse;
import edu.java.bot.services.clients.IScrapperClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListCommand implements Command {
    private static final String COMMAND_DESCRIPTION = "Show list of tracked links";
    private static final String COMMAND_NAME = "/list";

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
        ListLinksResponse links = scrapperClient.getLinks(update.message().chat().id());

        if (links.getSize() == 0) {
            return new SendMessage(update.message().chat().id(), "Right now you are not tracking any links.");
        }

        StringBuilder responseText = new StringBuilder("Tracking links: ");

        for (LinkResponse link : links.getLinks()) {
            responseText.append(link.getUrl().toString())
                .append("\n");
        }

        return new SendMessage(update.message().chat().id(), responseText.toString());
    }
}
