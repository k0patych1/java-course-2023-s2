package edu.java.repositories.jooq;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import static edu.java.domain.jooq.Tables.CHAT;

@RequiredArgsConstructor
public class JooqTgChatRepository implements IJooqTgChatRepository {
    private final DSLContext dsl;

    @Override
    public void delete(Long id) {
        dsl.delete(CHAT)
            .where(CHAT.ID.eq(id))
            .execute();
    }

    @Override
    public void save(Long id) {
        dsl.insertInto(CHAT)
            .set(CHAT.ID, id)
            .onDuplicateKeyIgnore()
            .execute();
    }
}
