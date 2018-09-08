package kz.desh.snowballs.server;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import kz.desh.snowballs.server.commands.executor.CommandExecutor;
import lombok.val;

public class ServerActor extends UntypedActor {
    private CommandExecutor commandExecutor;

    private ServerActor(ActorSystem system) {
        this.commandExecutor = new CommandExecutor(system);
    }

    public static Props props(ActorSystem system) {
        return Props.create(ServerActor.class, () -> new ServerActor(system));
    }

    @Override
    public void onReceive(Object message) {
        if (message instanceof String) {
            val command = (String) message;
            this.commandExecutor.execute(getSender(), command);
        }
    }
}