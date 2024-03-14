package edu.java.bot;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.commands.Command;
import edu.java.bot.commands.HelpCommand;
import edu.java.bot.commands.StartCommand;
import edu.java.bot.commands.TrackCommand;
import edu.java.bot.services.validators.GitHubValidator;
import edu.java.bot.services.validators.LinkValidator;
import edu.java.bot.services.validators.StackOverFlowValidator;
import edu.java.bot.services.validators.ValidatorRepo;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class CommandsTest {
    private Update getMockUpdate(String messageText) {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);

        Mockito.when(update.message()).thenReturn(message);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(message.text()).thenReturn(messageText);
        Mockito.when(chat.id()).thenReturn(239L);

        return update;
    }

    @Test
    public void startCommandTest() {
        Update update = getMockUpdate("/start");

        StartCommand startCommand = new StartCommand();

        assertTrue(startCommand.supports(update));

        assertThat(startCommand.handle(update).getParameters().get("text")).isEqualTo("""
        Hello! I'm a bot for tracking updates via links.
        Use /help to see available commands
        """);
    }

    @Test
    public void helpCommandTest() {
        Update update = getMockUpdate("/help");

        Command mockCommand = mock(Command.class);
        Command secondMockCommand = mock(Command.class);

        Mockito.when(mockCommand.command()).thenReturn("/test");
        Mockito.when(mockCommand.description()).thenReturn("do something");

        Mockito.when(secondMockCommand.command()).thenReturn("/test2");
        Mockito.when(secondMockCommand.description()).thenReturn("aboba");

        HelpCommand helpCommand = new HelpCommand(List.of(mockCommand, secondMockCommand));

        assertTrue(helpCommand.supports(update));

        assertThat(helpCommand.handle(update).getParameters().get("text")).isEqualTo("""
        /test - do something
        /test2 - aboba""");
    }

    @Test
    public void trackCommandWithoutLinkTest() {
        Update update = getMockUpdate("/track");

        List<LinkValidator> validatorList = List.of(new StackOverFlowValidator(), new GitHubValidator());
        ValidatorRepo validatorRepo = new ValidatorRepo(validatorList);
        TrackCommand trackCommand = new TrackCommand(validatorRepo);

        assertTrue(trackCommand.supports(update));

        assertThat(trackCommand.handle(update).getParameters().get("text"))
            .isEqualTo("Please provide a link to track along with the /track command.");
    }

    @Test
    public void trackCommandWithCorrectLinkTest() {
        Update update = getMockUpdate("/track https://github.com/pengrad/java-telegram-bot-api");

        List<LinkValidator> validatorList = List.of(new StackOverFlowValidator(), new GitHubValidator());
        ValidatorRepo validatorRepo = new ValidatorRepo(validatorList);
        TrackCommand trackCommand = new TrackCommand(validatorRepo);

        assertTrue(trackCommand.supports(update));

        assertThat(trackCommand.handle(update).getParameters().get("text"))
            .isEqualTo("Started tracking the link: https://github.com/pengrad/java-telegram-bot-api");
    }

    @Test
    public void trackCommandWithIncorrectLinkTest() {
        Update update = getMockUpdate("/track https://www.youtube.com/watch?v=SOvrw-rysuQ");

        List<LinkValidator> validatorList = List.of(new StackOverFlowValidator(), new GitHubValidator());
        ValidatorRepo validatorRepo = new ValidatorRepo(validatorList);
        TrackCommand trackCommand = new TrackCommand(validatorRepo);

        assertTrue(trackCommand.supports(update));

        assertThat(trackCommand.handle(update).getParameters().get("text"))
            .isEqualTo("Please provide a correct supportable link");
    }
}
