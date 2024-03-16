package edu.java.controllers;

import api.TgChatApi;
import edu.java.services.ITgChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ChatController implements TgChatApi {
    private final ITgChatService tgChatService;

    @Override
    public Mono<ResponseEntity<Void>> tgChatIdDelete(Long id, ServerWebExchange exchange) {
        tgChatService.unregister(id);
        return Mono.just(ResponseEntity.status(HttpStatus.OK).build());
    }

    @Override
    public Mono<ResponseEntity<Void>> tgChatIdPost(Long id, ServerWebExchange exchange) {
        tgChatService.register(id);
        return Mono.just(ResponseEntity.status(HttpStatus.OK).build());
    }
}
