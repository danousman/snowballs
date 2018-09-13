package kz.desh.snowballs.server.commands;

public interface CommandCallback {
    void callback(Object... args);
}