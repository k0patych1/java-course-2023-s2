package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class ListCommand implements Command {

    private static final String COMMAND_DESCRIPTION = "Show list of tracked links";
    private static final String COMMAND_NAME = "/list";

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
        //todo logic
        String responseText = "Sorry, this command is not supported in the current version of the bot";

        return new SendMessage(update.message().chat().id(), responseText);
    }
}
