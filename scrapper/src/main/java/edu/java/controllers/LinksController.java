package edu.java.controllers;

import api.LinksApi;
import edu.java.exceptions.LinkNotFoundException;
import edu.java.models.dto.Link;
import edu.java.services.ILinkService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import model.AddLinkRequest;
import model.LinkResponse;
import model.ListLinksResponse;
import model.RemoveLinkRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class LinksController implements LinksApi {
    private final ILinkService linkService;

    @Override
    public Mono<ResponseEntity<LinkResponse>> linksDelete(
        Long tgChatId,
        Mono<RemoveLinkRequest> removeLinkRequest,
        ServerWebExchange exchange
    ) {
        return removeLinkRequest.flatMap(request -> {
            URI url = request.getLink();

            if (!linkService.remove(url, tgChatId)) {
                return Mono.error(new LinkNotFoundException("Link " + url.toString() + " doesnt' tracking"));
            }

            LinkResponse linkResponse = new LinkResponse();
            linkResponse.setId(tgChatId);
            linkResponse.setUrl(url);

            return Mono.just(new ResponseEntity<>(linkResponse, HttpStatus.OK));
        });
    }

    @Override
    public Mono<ResponseEntity<ListLinksResponse>> linksGet(Long tgChatId, ServerWebExchange exchange) {
        List<Link> links = linkService.listAllWithChatId(tgChatId);

        ListLinksResponse response = new ListLinksResponse();

        List<LinkResponse> linkResponses = links.stream()
            .map(link -> {
                LinkResponse linkResponse = new LinkResponse();
                linkResponse.setId(link.getId());
                linkResponse.setUrl(URI.create(link.getUrl()));
                return linkResponse;
            })
            .toList();

        response.setLinks(linkResponses);
        response.setSize(linkResponses.size());

        return Mono.just(new ResponseEntity<>(response, HttpStatus.OK));
    }

    @Override
    public Mono<ResponseEntity<LinkResponse>> linksPost(
        Long tgChatId,
        Mono<AddLinkRequest> addLinkRequest,
        ServerWebExchange exchange
    ) {
        return addLinkRequest.flatMap(request -> {
            URI url = request.getLink();
            linkService.add(url, tgChatId);
            LinkResponse linkResponse = new LinkResponse();
            linkResponse.setId(tgChatId);
            linkResponse.setUrl(url);

            return Mono.just(new ResponseEntity<>(linkResponse, HttpStatus.OK));
        });
    }
}
