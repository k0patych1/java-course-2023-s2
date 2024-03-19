package edu.java.services.jdbc;

import edu.java.repositories.jdbc.JdbcTgChatRepository;
import edu.java.services.ITgChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcTgChatService implements ITgChatService {
    private final JdbcTgChatRepository jdbcTgChatRepository;

    @Override
    public void register(Long id) {
        jdbcTgChatRepository.save(id);
    }

    @Override
    public void unregister(Long id) {
        jdbcTgChatRepository.delete(id);
    }
}
