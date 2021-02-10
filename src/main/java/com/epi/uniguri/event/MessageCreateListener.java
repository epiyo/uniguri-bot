package com.epi.uniguri.event;

import com.epi.uniguri.processor.MessageProcessor;
import discord4j.core.event.domain.message.MessageCreateEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MessageCreateListener implements EventListener<MessageCreateEvent> {

    private final MessageProcessor messageProcessor;

    public MessageCreateListener(MessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
    }

    @Override
    public Class<MessageCreateEvent> getEventType() {
        return MessageCreateEvent.class;
    }

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        return messageProcessor.processMessage(event.getMessage());
    }

}
