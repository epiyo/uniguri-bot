package com.epi.uniguri.command;

import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public interface Command {
    String getCommand();
    Mono<Void> executeCommand(Message eventMessage);
}
