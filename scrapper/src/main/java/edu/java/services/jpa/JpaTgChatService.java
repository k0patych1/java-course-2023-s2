package edu.java.services.jpa;

import edu.java.domain.jpa.Link;
import edu.java.domain.jpa.Subscription;
import edu.java.domain.jpa.TgChat;
import edu.java.repositories.jpa.IJpaLinkRepository;
import edu.java.repositories.jpa.IJpaTgChatRepository;
import edu.java.services.ITgChatService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class JpaTgChatService implements ITgChatService {
    private final IJpaTgChatRepository tgChatRepository;

    private final IJpaLinkRepository linkRepository;

    @Override
    @Transactional
    public void register(Long tgChatId) {
        if (tgChatRepository.findById(tgChatId).isPresent()) {
            return;
        }

        TgChat tgChat = new TgChat();
        tgChat.setId(tgChatId);
        tgChatRepository.save(tgChat);
    }

    @Override
    @Transactional
    public void unregister(Long tgChatId) {
        TgChat tgChat = tgChatRepository.findById(tgChatId).orElseThrow();
        tgChat.getSubscriptions().clear();
    }

    @Override
    @Transactional
    public List<edu.java.models.dto.TgChat> listAllWithLink(Long linkId) {
        Optional<Link> link = linkRepository.findById(linkId);

        if (link.isEmpty()) {
            return Collections.emptyList();
        }

        List<Subscription> subscriptions = link.get().getSubscriptions();

        return subscriptions.stream()
            .map(subscription -> mapToDto(subscription.getChat()))
            .toList();
    }

    private edu.java.models.dto.TgChat mapToDto(TgChat tgChat) {
        edu.java.models.dto.TgChat dto = new edu.java.models.dto.TgChat();
        dto.setId(tgChat.getId());
        return dto;
    }
}
