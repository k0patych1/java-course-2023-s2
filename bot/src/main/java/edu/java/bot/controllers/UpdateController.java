package edu.java.bot.controllers;

import api.UpdatesApi;
import model.LinkUpdate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class UpdateController implements UpdatesApi {
    @Override
    public Mono<ResponseEntity<Void>> updatesPost(Mono<LinkUpdate> linkUpdate, ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok().build());
    }
}
