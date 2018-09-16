package kz.desh.snowballs.server.commands.executor;

import kz.desh.snowballs.server.commands.Command;
import kz.desh.snowballs.server.commands.CommandCallback;
import kz.desh.snowballs.server.commands.LoginCommand;
import kz.desh.snowballs.server.commands.SkillStudyCommand;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandExecutor {
    private static final int COMMAND_BEGIN_INDEX = 0;
    private static final int COMMAND_END_INDEX = 5;
    private static final int COMMAND_BODY_START_INDEX = 6;

    private final Map<String, Command> commands = new HashMap<>();

    @Autowired
    public CommandExecutor(
            LoginCommand loginCommand,
            SkillStudyCommand skillStudyCommand) {
        this.commands.put(LoginCommand.COMMAND, loginCommand);
        this.commands.put(SkillStudyCommand.COMMAND, skillStudyCommand);
    }

    public String execute(Long playerId, String command, CommandCallback callback) {
        val commandPrefix = command.substring(COMMAND_BEGIN_INDEX, COMMAND_END_INDEX);
        val commandPostfix = command.substring(COMMAND_BODY_START_INDEX);
        return this.commands.get(commandPrefix).execute(playerId, commandPostfix, callback);
    }
}