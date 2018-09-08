package kz.desh.snowballs.server.commands.executor;

import akka.actor.ActorRef;
import kz.desh.snowballs.server.commands.Command;
import kz.desh.snowballs.server.commands.LoginCommand;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandExecutor {
    private final Map<String, Command> commands = new HashMap<>();

    public CommandExecutor(LoginCommand loginCommand) {
        this.commands.put("00001", loginCommand);
    }

    public void execute(ActorRef sender, String command) {
        val commandPrefix = command.substring(0, 5);
        val commandPostfix = command.substring(6);
        this.commands.get(commandPrefix).execute(sender, commandPostfix);
    }
}