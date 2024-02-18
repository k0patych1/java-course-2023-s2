package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class StartCommand implements Command {
    private static final String DESCRIPTION = "Зарегистрировать пользователя";
    private static final String NAME = "/start";
    private static final String GREETING = """
        Привет! Я бот для отслеживания обновлений по ссылкам.
        Используй /help, чтобы увидеть доступные команды
        """;

    @Override
    public String command() {
        return NAME;
    }

    @Override
    public String description() {
        return DESCRIPTION;
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), GREETING);
    }
}
