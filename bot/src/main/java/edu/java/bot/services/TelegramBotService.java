package edu.java.bot.services;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import model.LinkUpdate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TelegramBotService {
    private final TelegramBot telegramBot;

    public void sendMessage(LinkUpdate linkUpdate) {
        for (Long chatId : linkUpdate.getTgChatIds()) {
            telegramBot.execute(new SendMessage(chatId, linkUpdate.getDescription()));
        }
    }
}
