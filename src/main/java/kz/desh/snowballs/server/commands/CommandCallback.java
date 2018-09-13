package kz.desh.snowballs.server.commands;

@FunctionalInterface
public interface CommandCallback {
    void call(Object... args);
}