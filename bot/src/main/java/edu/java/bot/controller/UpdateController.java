package edu.java.bot.controller;

import edu.java.bot.api.UpdatesApi;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import model.LinkUpdate;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public class UpdateController implements UpdatesApi {
    @Override
    public Mono<ResponseEntity<Void>> updatesPost(@Valid @RequestBody Mono<LinkUpdate> linkUpdate) {
        return Mono.just(ResponseEntity.ok().build());
    }
}
