package kz.desh.snowballs.server;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LoginActor extends UntypedActor {
    public static Props props() {
        return Props.create(LoginActor.class);
    }

    @Override
    public void onReceive(Object message) {
        if (message instanceof LoginActorCommand) {
            val command = (LoginActorCommand) message;
            getSender().tell("Hello, Demon! " + System.currentTimeMillis(), ActorRef.noSender());
            System.out.println(System.currentTimeMillis() + " Login actor sent command with login: " + command.getLogin());
        }
    }

    @RequiredArgsConstructor
    @Getter
    public static class LoginActorCommand {
        private final String login;
    }
}