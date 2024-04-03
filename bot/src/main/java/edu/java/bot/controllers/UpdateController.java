package edu.java.bot.controllers;

import api.UpdatesApi;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import model.LinkUpdate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class UpdateController implements UpdatesApi {
    private final TelegramBot telegramBot;

    @Override
    public Mono<ResponseEntity<Void>> updatesPost(Mono<LinkUpdate> linkUpdate, ServerWebExchange exchange) {
        return linkUpdate.flatMap(update -> {
            for (Long chatId : update.getTgChatIds()) {
                telegramBot.execute(new SendMessage(chatId, update.getDescription()));
            }
            return Mono.just(ResponseEntity.ok().build());
        });
    }
}
