package kz.desh.snowballs.server.commands;

import akka.actor.ActorRef;

public interface Command {
    default String[] splitCommand(String command) {
        return command.split(" ");
    }

    void execute(ActorRef sender, String command);
}