package edu.java.repositories.jooq;

import edu.java.models.dto.Link;
import edu.java.models.dto.TgChat;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import static edu.java.domain.jooq.Tables.SUBSCRIPTIONS;

@RequiredArgsConstructor
public class JooqSubscriptionRepository implements IJooqSubscriptionRepository {
    private final DSLContext dslContext;

    @Override
    public void save(Long chatId, Long linkId) {
        dslContext.insertInto(SUBSCRIPTIONS)
            .set(SUBSCRIPTIONS.CHAT_ID, chatId)
            .set(SUBSCRIPTIONS.LINK_ID, linkId)
            .execute();
    }

    @Override
    public boolean delete(Long chatId, Long linkId) {
        int deleted = dslContext.delete(SUBSCRIPTIONS)
            .where(SUBSCRIPTIONS.CHAT_ID.eq(chatId))
            .and(SUBSCRIPTIONS.LINK_ID.eq(linkId))
            .execute();

        return deleted > 0;
    }

    @Override
    public List<Link> findAllLinksWithChatId(Long chatId) {
        return dslContext.selectFrom(SUBSCRIPTIONS)
            .where(SUBSCRIPTIONS.CHAT_ID.eq(chatId))
            .fetchInto(Link.class);
    }

    @Override
    public List<TgChat> findAllChatsWithLinkId(Long linkId) {
        return dslContext
            .selectFrom(SUBSCRIPTIONS)
            .where(SUBSCRIPTIONS.LINK_ID.eq(linkId))
            .fetchInto(TgChat.class);
    }
}
