package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.services.clients.ScrapperClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StartCommand implements Command {
    private static final String COMMAND_NAME = "/start";
    private static final String COMMAND_DESCRIPTION = "register a user";
    private static final String GREETING = """
        Hello! I'm a bot for tracking updates via links.
        Use /help to see available commands
        """;

    private final ScrapperClient scrapperClient;

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
        scrapperClient.registerChat(update.message().chat().id());

        return new SendMessage(update.message().chat().id(), GREETING);
    }
}
