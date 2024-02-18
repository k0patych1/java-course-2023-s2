package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import edu.java.bot.commands.Command;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.List;

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
            if (update != null) {
                execute(userMessageProcessor.process(update));
            }
        }

        return CONFIRMED_UPDATES_ALL;
    }

    @Override
    @PostConstruct
    public void start() {
        telegramBot.setUpdatesListener(this);
        initMenu();
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
