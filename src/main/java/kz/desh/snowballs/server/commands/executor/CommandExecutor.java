package kz.desh.snowballs.server.commands.executor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import kz.desh.snowballs.server.commands.Command;
import kz.desh.snowballs.server.commands.LoginCommand;
import lombok.val;

import java.util.HashMap;
import java.util.Map;

public class CommandExecutor {
    private final Map<String, Command> commands = new HashMap<>();

    public CommandExecutor(ActorSystem system) {
        this.commands.put("00001", new LoginCommand(system));
    }

    public void execute(ActorRef sender, String command) {
        val commandPrefix = command.substring(0, 5);
        val commandPostfix = command.substring(6);
        this.commands.get(commandPrefix).execute(sender, commandPostfix);
    }
}