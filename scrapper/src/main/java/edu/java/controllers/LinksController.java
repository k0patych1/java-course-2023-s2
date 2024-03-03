package edu.java.controllers;

import edu.java.api.LinksApi;
import edu.java.models.dto.request.AddLinkRequest;
import edu.java.models.dto.request.RemoveLinkRequest;
import edu.java.models.dto.response.LinkResponse;
import edu.java.models.dto.response.ListLinksResponse;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public class LinksController implements LinksApi {
    @Override
    public Mono<ResponseEntity<LinkResponse>> linksDelete(Long tgChatId, Mono<RemoveLinkRequest> removeLinkRequest) {
        return null;
    }

    @Override
    public Mono<ResponseEntity<ListLinksResponse>> linksGet(Long tgChatId) {
        return null;
    }

    @Override
    public Mono<ResponseEntity<LinkResponse>> linksPost(Long tgChatId, Mono<AddLinkRequest> addLinkRequest) {
        return null;
    }
}
