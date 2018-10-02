package kz.desh.snowballs.server.commands.executor;

import kz.desh.snowballs.server.commands.*;
import kz.desh.snowballs.server.entity.PlayerEntity;
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
            StudySkillCommand studySkillCommand,
            CancelStudySkillCommand cancelStudySkillCommand,
            StudyAbilityCommand studyAbilityCommand,
            CancelStudyAbilityCommand cancelStudyAbilityCommand,
            RetrieveActionCommand retrieveActionCommand,
            RetrieveStorageCommand retrieveStorageCommand,
            RetrieveSkillsCommand retrieveSkillsCommand,
            RetrieveAbilitiesCommand retrieveAbilitiesCommand,
            RetrieveFinishedActionCommand retrieveFinishedActionCommand,
            RetrieveItemsCommand retrieveItemsCommand) {
        //processes
        this.commands.put(LoginCommand.COMMAND, loginCommand);
        this.commands.put(StudySkillCommand.COMMAND, studySkillCommand);
        this.commands.put(CancelStudySkillCommand.COMMAND, cancelStudySkillCommand);
        this.commands.put(StudyAbilityCommand.COMMAND, studyAbilityCommand);
        this.commands.put(CancelStudyAbilityCommand.COMMAND, cancelStudyAbilityCommand);

        //retrieve information
        this.commands.put(RetrieveStorageCommand.COMMAND, retrieveStorageCommand);
        this.commands.put(RetrieveSkillsCommand.COMMAND, retrieveSkillsCommand);
        this.commands.put(RetrieveActionCommand.COMMAND, retrieveActionCommand);
        this.commands.put(RetrieveAbilitiesCommand.COMMAND, retrieveAbilitiesCommand);
        this.commands.put(RetrieveFinishedActionCommand.COMMAND, retrieveFinishedActionCommand);
        this.commands.put(RetrieveItemsCommand.COMMAND, retrieveItemsCommand);
    }

    public String execute(PlayerEntity player, String command, CommandCallback callback) {
        val commandPrefix = command.substring(COMMAND_BEGIN_INDEX, COMMAND_END_INDEX);
        val commandPostfix = command.substring(COMMAND_BODY_START_INDEX);
        return this.commands.get(commandPrefix).execute(player, commandPostfix, callback);
    }
}