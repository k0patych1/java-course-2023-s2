package edu.java.controllers;

import edu.java.api.TgChatApi;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public class ChatController implements TgChatApi {
    @Override
    public Mono<ResponseEntity<Void>> tgChatIdDelete(Long id) {
        return Mono.just(ResponseEntity.ok().build());
    }

    @Override
    public Mono<ResponseEntity<Void>> tgChatIdPost(Long id) {
        return Mono.just(ResponseEntity.ok().build());
    }
}
