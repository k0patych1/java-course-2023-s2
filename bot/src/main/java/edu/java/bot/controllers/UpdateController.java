package edu.java.bot.controllers;

import api.UpdatesApi;
import edu.java.bot.services.TelegramBotService;
import lombok.RequiredArgsConstructor;
import model.LinkUpdate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class UpdateController implements UpdatesApi {
    private final TelegramBotService telegramBot;

    @Override
    public Mono<ResponseEntity<Void>> updatesPost(Mono<LinkUpdate> linkUpdate, ServerWebExchange exchange) {
        telegramBot.sendMessage(linkUpdate.block());

        return Mono.just(ResponseEntity.ok().build());
    }
}
