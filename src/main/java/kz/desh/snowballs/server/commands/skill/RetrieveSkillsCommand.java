package kz.desh.snowballs.server.commands.skill;

import kz.desh.snowballs.server.commands.Command;
import kz.desh.snowballs.server.commands.CommandCallback;
import kz.desh.snowballs.server.entity.PlayerEntity;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RetrieveSkillsCommand implements Command {
    public static final String COMMAND = "10002";

    private static final String RESPONSE_COMMAND = COMMAND +
            " %s"; //skills

    @Override
    public String execute(PlayerEntity player, String command, CommandCallback callback) {
        return createResponse(player);
    }

    private String createResponse(PlayerEntity player) {
        val skillCommand = "[" +
                "%d;" +   //id
                "%s;" +   //name rus
                "%s;" +   //name eng
                "%s;" +   //type
                "%d;" +   //current level
                "%d;" +   //max level
                "%.2f;" + //current bonus
                "%d;" +   //cost for the next level
                "%d" +    //study time for the next level
                "]";
        return String.format(RESPONSE_COMMAND,
                player.getSkills().stream()
                        .map(skillEntity -> {
                            val type = skillEntity.getType();
                            val currentLevel = skillEntity.getCurrentLevel();
                            return String.format(skillCommand,
                                    skillEntity.getId(),
                                    skillEntity.getNameRus(),
                                    skillEntity.getNameEng(),
                                    type,
                                    currentLevel,
                                    skillEntity.getMaxLevel(),
                                    type.getBonus(currentLevel),
                                    type.getLevelCost(currentLevel + 1),
                                    type.getStudyTime(currentLevel + 1));
                        })
                        .collect(Collectors.joining(" "))
        );
    }
}