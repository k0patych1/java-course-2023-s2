package edu.java.bot.services.listeners;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.response.BaseResponse;
import jakarta.annotation.PostConstruct;
import java.util.List;

public interface IUpdatesListenerBot extends AutoCloseable, UpdatesListener {
    <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request);

    @Override
    int process(List<Update> updates);

    @PostConstruct
    void start();

    @Override
    void close();
}
