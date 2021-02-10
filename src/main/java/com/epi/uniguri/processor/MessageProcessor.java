package com.epi.uniguri.processor;

import com.epi.uniguri.command.CommandExecutorFactory;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MessageProcessor {

    private final CommandExecutorFactory commandExecutorFactory;

    public MessageProcessor(CommandExecutorFactory commandExecutorFactory) {
        this.commandExecutorFactory = commandExecutorFactory;
    }

    public Mono<Void> processMessage(Message message) {
        return Mono.just(message)
                .filter(m -> m.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(m -> commandExecutorFactory.getCommandExecutors().containsKey(message.getContent()))
                .flatMap(m -> commandExecutorFactory.getCommandExecutor(message.getContent()).executeCommand(message))
                .then();
    }

}
