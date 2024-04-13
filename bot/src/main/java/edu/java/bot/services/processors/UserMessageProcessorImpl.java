package edu.java.bot.services.processors;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.Command;
import io.micrometer.core.instrument.Counter;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMessageProcessorImpl implements UserMessageProcessor {
    private final Counter processedMessagesCounter;

    private static final String UNKNOWN_COMMAND = "Unknown command";

    private final List<? extends Command> commands;

    @Override
    public List<? extends Command> commands() {
        return commands;
    }

    @Override
    public SendMessage process(@NonNull Update update) {
        if (commands != null) {
            for (Command command : commands) {
                if (command.supports(update)) {
                    SendMessage message = command.handle(update);
                    processedMessagesCounter.increment();
                    return message;
                }
            }
        }

        return new SendMessage(update.message().chat().id(), UNKNOWN_COMMAND);
    }
}
