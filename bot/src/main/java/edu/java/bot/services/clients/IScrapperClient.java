package edu.java.bot.services.clients;

import edu.java.bot.models.dto.request.AddLinkRequest;
import edu.java.bot.models.dto.request.RemoveLinkRequest;
import edu.java.bot.models.dto.response.LinkResponse;
import edu.java.bot.models.dto.response.ListLinksResponse;

public interface IScrapperClient {
    String registerChat(Long id);

    String deleteChat(Long id);

    LinkResponse addLink(Long tgChatId, AddLinkRequest addLinkRequest);

    LinkResponse removeLink(Long tgChatId, RemoveLinkRequest removeLinkRequest);

    ListLinksResponse getLinks(Long tgChatId);
}
