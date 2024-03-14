package edu.java.bot.services.processors;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.Command;
import java.util.List;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMessageProcessorImpl implements UserMessageProcessor {
    private static final String UNKNOWN_COMMAND = "Unknown command";

    private final List<? extends Command> commands;

    @Autowired
    public UserMessageProcessorImpl(List<? extends Command> commands) {
        this.commands = commands;
    }

    @Override
    public List<? extends Command> commands() {
        return commands;
    }

    @Override
    public SendMessage process(@NonNull Update update) {
        if (commands != null) {
            for (Command command : commands) {
                if (command.supports(update)) {
                    return command.handle(update);
                }
            }
        }

        return new SendMessage(update.message().chat().id(), UNKNOWN_COMMAND);
    }
}
