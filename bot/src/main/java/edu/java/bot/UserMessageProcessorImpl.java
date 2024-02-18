package edu.java.bot;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.Command;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserMessageProcessorImpl implements UserMessageProcessor {
    private static final String UNKNOWN_COMMAND = "Unknown command";

    @Autowired
    private List<? extends Command> commands;

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
