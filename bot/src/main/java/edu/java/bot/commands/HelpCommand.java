package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand implements Command {
    private static final String COMMAND_NAME = "/help";
    private static final String COMMAND_DESCRIPTION = "display a window with commands";
    private final List<? extends Command> commands;

    @Autowired
    public HelpCommand(List<? extends Command> commands) {
        this.commands = commands;
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
        String help = commands
            .stream()
            .map(command -> command.command() + " - " + command.description())
            .collect(Collectors.joining("\n"));

        return new SendMessage(update.message().chat().id(), help);
    }
}
