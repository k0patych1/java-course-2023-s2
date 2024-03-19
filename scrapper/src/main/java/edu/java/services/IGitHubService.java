package edu.java.services;

import edu.java.models.GitHubRepoLastUpdate;
import edu.java.models.dto.Link;
import edu.java.models.dto.TgChat;
import edu.java.models.dto.request.LinkUpdate;
import java.util.List;

public interface IGitHubService {
    LinkUpdate formUpdate(GitHubRepoLastUpdate lastUpdate, Link link, List<TgChat> tgChats);
}
