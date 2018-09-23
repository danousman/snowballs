package kz.desh.snowballs.server.commands;

import kz.desh.snowballs.server.entity.PlayerEntity;

public interface Command {
    default String[] splitCommand(String command) {
        return command.split(" ");
    }

    String execute(PlayerEntity player, String command, CommandCallback callback);
}