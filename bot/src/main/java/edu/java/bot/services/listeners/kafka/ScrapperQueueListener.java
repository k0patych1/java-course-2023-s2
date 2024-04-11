package edu.java.bot.services.listeners.kafka;

import edu.java.bot.services.TelegramBotService;
import lombok.RequiredArgsConstructor;
import model.LinkUpdate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScrapperQueueListener {
    private final TelegramBotService telegramBotService;

    @KafkaListener(topics = "messages.updates", containerFactory = "kafkaListenerContainerFactory")
    public void listen(LinkUpdate update) {
        telegramBotService.sendMessage(update);
    }
}
