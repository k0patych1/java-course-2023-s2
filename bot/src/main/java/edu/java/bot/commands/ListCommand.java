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

        StringBuilder responseText = new StringBuilder("Tracked links:\n");

        for (LinkResponse link : links.getLinks()) {
            responseText.append(link.getUrl().toString())
                .append("\n");
        }

        return new SendMessage(update.message().chat().id(), responseText.toString());
    }
}
