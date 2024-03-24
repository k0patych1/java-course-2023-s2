package edu.java.services.jpa;

import edu.java.domain.jpa.TgChat;
import edu.java.repositories.jpa.IJpaLinkRepository;
import edu.java.repositories.jpa.IJpaTgChatRepository;
import edu.java.services.ITgChatService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaTgChatService implements ITgChatService {
    private final IJpaLinkRepository linkRepository;

    private final IJpaTgChatRepository tgChatRepository;

    @Override
    public void register(Long tgChatId) {
        TgChat tgChat = new TgChat();
        tgChat.setId(tgChatId);
        tgChatRepository.save(tgChat);
    }

    @Override
    public void unregister(Long tgChatId) {
        tgChatRepository.deleteById(tgChatId);

    }

    @Override
    public List<edu.java.models.dto.TgChat> listAllWithLink(Long linkId) {
        return null;
    }
}
