package kz.desh.snowballs.server;

import akka.actor.Props;
import akka.actor.UntypedActor;
import kz.desh.snowballs.server.commands.executor.CommandExecutor;
import lombok.val;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ServerActor extends UntypedActor {
    private CommandExecutor commandExecutor;

    private ServerActor(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    public static Props props(CommandExecutor commandExecutor) {
        return Props.create(ServerActor.class, () -> new ServerActor(commandExecutor));
    }

    @Override
    public void onReceive(Object message) {
        if (message instanceof String) {
            val command = (String) message;
            this.commandExecutor.execute(getSender(), command);
        }
    }
}