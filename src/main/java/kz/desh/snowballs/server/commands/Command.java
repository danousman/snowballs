package kz.desh.snowballs.server.commands;

public interface Command {
    default String[] splitCommand(String command) {
        return command.split(" ");
    }

    String execute(Long playerId, String command, CommandCallback callback);
}