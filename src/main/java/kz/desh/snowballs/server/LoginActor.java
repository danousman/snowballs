package kz.desh.snowballs.server;

import akka.actor.AbstractActor;
import akka.actor.Props;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class LoginActor extends AbstractActor {
    public static Props props() {
        return Props.create(LoginActor.class, LoginActor::new);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(LoginActorCommand.class, command -> System.out.println("Login actor received command with login: " + command.getLogin()))
                .build();
    }

    @RequiredArgsConstructor
    @Getter
    public static class LoginActorCommand {
        private final String login;
    }
}