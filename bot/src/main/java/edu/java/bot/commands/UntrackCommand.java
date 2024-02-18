package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class UntrackCommand implements Command {
    private static final String COMMAND_DESCRIPTION = "stop tracking the link";
    private static final String COMMAND_NAME = "/untrack";

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

        //todo logic of tracking url (repository of tracking urls)

        String responseText = "Sorry, this command is not supported in the current version of the bot";

        return new SendMessage(update.message().chat().id(), responseText);
    }
}
