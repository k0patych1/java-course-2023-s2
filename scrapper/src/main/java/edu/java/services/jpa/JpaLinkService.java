package edu.java.services.jpa;

import edu.java.models.dto.Link;
import edu.java.repositories.jpa.IJpaLinkRepository;
import edu.java.repositories.jpa.IJpaTgChatRepository;
import edu.java.services.ILinkService;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaLinkService implements ILinkService {
    private final IJpaTgChatRepository tgChatRepository;

    private final IJpaLinkRepository linkRepository;

    @Override
    public void add(URI url, Long chatId) {
    }

    @Override
    public boolean remove(URI url, Long chatId) {
        return false;
    }

    @Override
    public void update(Long linkId, OffsetDateTime lastCheckedAt) {

    }

    @Override
    public List<Link> listAllWithChatId(Long chatId) {
        return null;
    }

    @Override
    public List<Link> listOldChecked(OffsetDateTime interval) {
        return null;
    }
}
