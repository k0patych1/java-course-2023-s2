package edu.java.services.jpa;

import edu.java.domain.jpa.Subscription;
import edu.java.domain.jpa.TgChat;
import edu.java.repositories.jpa.IJpaSubscriptionRepository;
import edu.java.repositories.jpa.IJpaTgChatRepository;
import edu.java.services.ITgChatService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class JpaTgChatService implements ITgChatService {
    private final IJpaTgChatRepository tgChatRepository;

    private final IJpaSubscriptionRepository subscriptionRepository;

    @Override
    public void register(Long tgChatId) {
        TgChat tgChat = new TgChat();
        tgChat.setId(tgChatId);
        tgChatRepository.save(tgChat);
    }

    @Override
    @Transactional
    public void unregister(Long tgChatId) {
        Optional<TgChat> optionalTgChat = tgChatRepository.findById(tgChatId);

        if (optionalTgChat.isPresent()) {
            TgChat tgChat = optionalTgChat.get();
            subscriptionRepository.deleteByTgChatId(tgChat.getId());
        }
    }

    @Override
    public List<edu.java.models.dto.TgChat> listAllWithLink(Long linkId) {
        List<Subscription> subscriptions = subscriptionRepository.findAllByLinkId(linkId);

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
