package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import edu.java.bot.commands.Command;
import edu.java.bot.processor.UserMessageProcessorImpl;
import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateTrackerBot implements Bot {
    private final TelegramBot telegramBot;

    private final UserMessageProcessorImpl userMessageProcessor;

    @Override
    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        telegramBot.execute(request);
    }

    @Override
    public int process(List<Update> updates) {
        for (Update update : updates) {
            if (update != null && update.message() != null) {
                execute(userMessageProcessor.process(update));
            }
        }

        return CONFIRMED_UPDATES_ALL;
    }

    @Override
    @PostConstruct
    public void start() {
        initMenu();
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public void close() {
        telegramBot.removeGetUpdatesListener();
    }

    private void initMenu() {
        BotCommand[] menu = userMessageProcessor.commands().stream()
            .map(Command::toApiCommand)
            .toArray(BotCommand[]::new);
        execute(new SetMyCommands(menu));
    }
}
