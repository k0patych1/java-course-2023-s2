package edu.java.controllers;

import api.TgChatApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
public class ChatController implements TgChatApi {
    @Override
    public Mono<ResponseEntity<Void>> tgChatIdDelete(Long id, ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok().build());
    }

    @Override
    public Mono<ResponseEntity<Void>> tgChatIdPost(Long id, ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok().build());
    }
}
