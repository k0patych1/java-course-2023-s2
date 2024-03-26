package edu.java.services.jpa;

import edu.java.domain.jpa.Link;
import edu.java.domain.jpa.Subscription;
import edu.java.domain.jpa.TgChat;
import edu.java.repositories.jpa.IJpaLinkRepository;
import edu.java.repositories.jpa.IJpaSubscriptionRepository;
import edu.java.repositories.jpa.IJpaTgChatRepository;
import edu.java.services.ILinkService;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class JpaLinkService implements ILinkService {
    private final IJpaTgChatRepository tgChatRepository;

    private final IJpaLinkRepository linkRepository;

    private final IJpaSubscriptionRepository subscriptionRepository;

    @Override
    @Transactional
    public void add(URI url, Long chatId) {
        if (subscriptionRepository.findByUrlAndChatId(url.toString(), chatId).isPresent()) {
            return;
        }

        Optional<Link> linkOptional = linkRepository.findByUrl(url.toString());
        Link link = new Link();
        if (linkOptional.isEmpty()) {
            link.setUrl(url.toString());
            link.setLastCheckTime(OffsetDateTime.now());
        } else {
            link = linkOptional.get();
        }

        Subscription subscription = new Subscription();

        TgChat chat;

        if (tgChatRepository.findById(chatId).isPresent()) {
            chat = tgChatRepository.findById(chatId).orElseThrow();
        } else {
            chat = new TgChat();
            chat.setId(chatId);
        }

        subscription.setChat(chat);
        subscription.setLink(link);
        subscription.setCreatedAt(OffsetDateTime.now());

        subscriptionRepository.save(subscription);
    }

    @Override
    @Transactional
    public boolean remove(URI url, Long chatId) {
        Optional<Subscription> subscriptionOptional = subscriptionRepository.findByUrlAndChatId(url.toString(), chatId);

        if (subscriptionOptional.isEmpty()) {
            return false;
        }

        Link link = subscriptionOptional.get().getLink();

        subscriptionRepository.deleteById(subscriptionOptional.get().getId());
        linkRepository.flush();

        if (link.getSubscriptions().isEmpty()) {
            linkRepository.delete(link);
        }

        return true;
    }

    @Override
    @Transactional
    public void update(Long linkId, OffsetDateTime lastCheckedAt) {
        Link link = linkRepository.findById(linkId).orElseThrow();
        link.setLastCheckTime(lastCheckedAt);
    }

    @Override
    @Transactional
    public List<edu.java.models.dto.Link> listAllWithChatId(Long chatId) {
        Optional<TgChat> tgChat = tgChatRepository.findById(chatId);

        return tgChat.map(chat -> chat.getSubscriptions()
            .stream()
            .map((subscription) -> mapToDto(subscription.getLink()))
            .toList()).orElse(Collections.emptyList());

    }

    @Override
    public List<edu.java.models.dto.Link> listOldChecked(OffsetDateTime interval) {
        List<Link> links = linkRepository.findAllByLastCheckTimeBefore(interval);

        return links.stream()
            .map(this::mapToDto)
            .toList();
    }

    private edu.java.models.dto.Link mapToDto(edu.java.domain.jpa.Link link) {
        return new edu.java.models.dto.Link(link.getId(), link.getUrl(), link.getLastCheckTime());
    }
}
