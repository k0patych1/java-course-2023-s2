package edu.java.bot;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.Command;
import edu.java.bot.services.processors.UserMessageProcessor;
import edu.java.bot.services.processors.UserMessageProcessorImpl;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;

public class UserMessageProcessorTest {
    private final Command command = mock(Command.class);

    @Test
    public void messageProcessorCorrectCommandTest() {
        var helpMessageUpdate = mock(Update.class);
        SendMessage expectedSendMessage = new SendMessage(666l, "answer");

        Mockito.when(command.command()).thenReturn("/test");
        Mockito.when(command.handle(helpMessageUpdate)).thenReturn(expectedSendMessage);
        Mockito.when(command.supports(helpMessageUpdate)).thenReturn(true);

        List<Command> commands = List.of(command);

        UserMessageProcessor messageProcessor = new UserMessageProcessorImpl(commands);
        ;
        SendMessage sendMessage = messageProcessor.process(helpMessageUpdate);

        assertThat(sendMessage.getParameters()).isEqualTo(expectedSendMessage.getParameters());
    }

    @Test
    public void messageProcessorIncorrectCommandTest() {
        var helpMessageUpdate = mock(Update.class);
        var message = mock(Message.class);
        var chat = mock(Chat.class);

        Mockito.when(helpMessageUpdate.message()).thenReturn(message);
        Mockito.when(message.chat()).thenReturn(chat);
        Mockito.when(chat.id()).thenReturn(666L);

        SendMessage expectedSendMessage = new SendMessage(666l, "Unknown command");

        Mockito.when(command.command()).thenReturn("/test");
        Mockito.when(command.handle(helpMessageUpdate)).thenReturn(expectedSendMessage);
        Mockito.when(command.supports(helpMessageUpdate)).thenReturn(false);

        List<Command> commands = List.of(command);

        UserMessageProcessor messageProcessor = new UserMessageProcessorImpl(commands);
        ;
        SendMessage sendMessage = messageProcessor.process(helpMessageUpdate);

        assertThat(sendMessage.getParameters()).isEqualTo(expectedSendMessage.getParameters());
    }
}
