package edu.java.services;

import edu.java.models.dto.Link;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

public interface ILinkService {
    void add(URI url, Long chatId);

    void remove(URI url, Long chatId);

    void update(Long linkId, OffsetDateTime lastCheckedAt);

    List<Link> listAllWithChatId(Long chatId);

    List<Link> listAll();

    List<Link> listOldChecked(OffsetDateTime interval);
}
