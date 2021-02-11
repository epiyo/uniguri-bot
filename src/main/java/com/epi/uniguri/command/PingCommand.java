package com.epi.uniguri.command;

import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PingCommand implements Command {

    @Override
    public String getCommand() {
        return "ping";
    }

    @Override
    public Mono<Void> executeCommand(Message eventMessage) {
        return Mono.just(eventMessage)
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage("pong"))
                .then();
    }

}
