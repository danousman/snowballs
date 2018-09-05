package kz.desh.snowballs.server;

import akka.actor.AbstractActor;
import akka.actor.ActorSystem;
import akka.actor.Props;
import kz.desh.snowballs.server.commands.executor.CommandExecutor;

public class ServerActor extends AbstractActor {
    private CommandExecutor commandExecutor;

    private ServerActor(ActorSystem system) {
        this.commandExecutor = new CommandExecutor(system);
    }

    public static Props props(ActorSystem system) {
        return Props.create(ServerActor.class, () -> new ServerActor(system));
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, command -> this.commandExecutor.execute(getSender(), command))
                .build();
    }
}