package kz.desh.snowballs.server.commands;

import kz.desh.snowballs.server.commands.ability.CancelStudyAbilityCommand;
import kz.desh.snowballs.server.commands.ability.RetrieveAbilitiesCommand;
import kz.desh.snowballs.server.commands.ability.StudyAbilityCommand;
import kz.desh.snowballs.server.commands.action.RetrieveActionCommand;
import kz.desh.snowballs.server.commands.action.RetrieveFinishedActionCommand;
import kz.desh.snowballs.server.commands.battle.CancelSearchBattleCommand;
import kz.desh.snowballs.server.commands.battle.StartSearchBattleCommand;
import kz.desh.snowballs.server.commands.item.PutOnClothesCommand;
import kz.desh.snowballs.server.commands.item.RetrieveItemsCommand;
import kz.desh.snowballs.server.commands.item.TakeOffClothesCommand;
import kz.desh.snowballs.server.commands.player.LoginCommand;
import kz.desh.snowballs.server.commands.player.RetrievePlayerCharacteristicsCommand;
import kz.desh.snowballs.server.commands.player.RetrievePlayerClothesCommand;
import kz.desh.snowballs.server.commands.skill.CancelStudySkillCommand;
import kz.desh.snowballs.server.commands.skill.RetrieveSkillsCommand;
import kz.desh.snowballs.server.commands.skill.StudySkillCommand;
import kz.desh.snowballs.server.commands.storage.RetrieveStorageCommand;
import kz.desh.snowballs.server.commands.storage.RetrieveStorageItemsCommand;
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
            PutOnClothesCommand putOnClothesCommand,
            TakeOffClothesCommand takeOffClothesCommand,

            RetrieveActionCommand retrieveActionCommand,
            RetrieveStorageCommand retrieveStorageCommand,
            RetrieveSkillsCommand retrieveSkillsCommand,
            RetrieveAbilitiesCommand retrieveAbilitiesCommand,
            RetrieveFinishedActionCommand retrieveFinishedActionCommand,
            RetrieveItemsCommand retrieveItemsCommand,
            RetrieveStorageItemsCommand retrieveStorageItemsCommand,
            RetrievePlayerClothesCommand retrievePlayerClothesCommand,
            RetrievePlayerCharacteristicsCommand retrievePlayerCharacteristicsCommand,

            StartSearchBattleCommand startSearchBattleCommand,
            CancelSearchBattleCommand cancelSearchBattleCommand) {
        //processes
        this.commands.put(LoginCommand.COMMAND, loginCommand);
        this.commands.put(StudySkillCommand.COMMAND, studySkillCommand);
        this.commands.put(CancelStudySkillCommand.COMMAND, cancelStudySkillCommand);
        this.commands.put(StudyAbilityCommand.COMMAND, studyAbilityCommand);
        this.commands.put(CancelStudyAbilityCommand.COMMAND, cancelStudyAbilityCommand);
        this.commands.put(PutOnClothesCommand.COMMAND, putOnClothesCommand);
        this.commands.put(TakeOffClothesCommand.COMMAND, takeOffClothesCommand);

        //retrieve information
        this.commands.put(RetrieveStorageCommand.COMMAND, retrieveStorageCommand);
        this.commands.put(RetrieveSkillsCommand.COMMAND, retrieveSkillsCommand);
        this.commands.put(RetrieveActionCommand.COMMAND, retrieveActionCommand);
        this.commands.put(RetrieveAbilitiesCommand.COMMAND, retrieveAbilitiesCommand);
        this.commands.put(RetrieveFinishedActionCommand.COMMAND, retrieveFinishedActionCommand);
        this.commands.put(RetrieveItemsCommand.COMMAND, retrieveItemsCommand);
        this.commands.put(RetrieveStorageItemsCommand.COMMAND, retrieveStorageItemsCommand);
        this.commands.put(RetrievePlayerClothesCommand.COMMAND, retrievePlayerClothesCommand);
        this.commands.put(RetrievePlayerCharacteristicsCommand.COMMAND, retrievePlayerCharacteristicsCommand);

        //battle
        this.commands.put(StartSearchBattleCommand.COMMAND, startSearchBattleCommand);
        this.commands.put(CancelSearchBattleCommand.COMMAND, cancelSearchBattleCommand);
    }

    public String execute(PlayerEntity player, String command, CommandCallback callback) {
        val commandPrefix = command.substring(COMMAND_BEGIN_INDEX, COMMAND_END_INDEX);
        val commandPostfix = command.substring(COMMAND_BODY_START_INDEX);
        return this.commands.get(commandPrefix).execute(player, commandPostfix, callback);
    }
}