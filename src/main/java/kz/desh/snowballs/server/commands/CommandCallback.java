package kz.desh.snowballs.server.commands;

public interface CommandCallback {
    void call(Object... args);
}