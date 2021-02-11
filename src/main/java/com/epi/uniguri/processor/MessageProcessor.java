package com.epi.uniguri.processor;

import com.epi.uniguri.command.CommandExecutorFactory;
import discord4j.core.object.entity.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MessageProcessor {

    @Value("${prefix}")
    private String prefix;

    private final CommandExecutorFactory commandExecutorFactory;


    public MessageProcessor(CommandExecutorFactory commandExecutorFactory) {
        this.commandExecutorFactory = commandExecutorFactory;
    }

    public Mono<Void> processMessage(Message message) {
        return Mono.just(message)
                .filter(msg -> msg.getAuthor().map(user -> !user.isBot() && message.getContent().startsWith(prefix)).orElse(false))
                .map(msg -> msg.getContent().substring(prefix.length()).strip())
                .filter(msg -> commandExecutorFactory.getCommandExecutors().containsKey(msg))
                .flatMap(msg -> commandExecutorFactory
                        .getCommandExecutor(msg)
                        .executeCommand(message))
                .then();
    }

}
