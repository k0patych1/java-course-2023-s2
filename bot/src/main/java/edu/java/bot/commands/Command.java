package edu.java.bot.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.NonNull;

public interface Command {
    String command();

    String description();

    SendMessage handle(Update update);

    default boolean supports(@NonNull Update update) {
        String message = update.message().text();

        return message.startsWith(command());
    }

    default BotCommand toApiCommand() {
        return new BotCommand(command(), description());
    }
}
