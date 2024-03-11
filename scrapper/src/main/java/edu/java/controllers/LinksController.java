package edu.java.controllers;

import api.LinksApi;
import model.AddLinkRequest;
import model.LinkResponse;
import model.ListLinksResponse;
import model.RemoveLinkRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
public class LinksController implements LinksApi {
    @Override
    public Mono<ResponseEntity<LinkResponse>> linksDelete(
        Long tgChatId,
        Mono<RemoveLinkRequest> removeLinkRequest,
        ServerWebExchange exchange
    ) {
        return Mono.just(ResponseEntity.ok().build());
    }

    @Override
    public Mono<ResponseEntity<ListLinksResponse>> linksGet(Long tgChatId, ServerWebExchange exchange) {
        return Mono.just(ResponseEntity.ok().build());
    }

    @Override
    public Mono<ResponseEntity<LinkResponse>> linksPost(
        Long tgChatId,
        Mono<AddLinkRequest> addLinkRequest,
        ServerWebExchange exchange
    ) {
        return Mono.just(ResponseEntity.ok().build());
    }
}
