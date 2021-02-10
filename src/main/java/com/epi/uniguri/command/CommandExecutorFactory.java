package com.epi.uniguri.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommandExecutorFactory {

    private final Map<String, Command> commandExecutors = new HashMap<>();

    @Autowired
    private CommandExecutorFactory(List<Command> services) {
        for(Command handler : services) {
            commandExecutors.put(handler.getCommand(), handler);
        }
    }

    public Command getCommandExecutor(String command) {
        return commandExecutors.get(command);
    }

    public Map<String, Command> getCommandExecutors() {
        return commandExecutors;
    }
}
