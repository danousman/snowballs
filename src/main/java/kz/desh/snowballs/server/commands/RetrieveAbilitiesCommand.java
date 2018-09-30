package kz.desh.snowballs.server.commands;

import kz.desh.snowballs.server.entity.PlayerEntity;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component
public class RetrieveAbilitiesCommand implements Command {
    public static final String COMMAND = "10004";

    private static final String RESPONSE_COMMAND = COMMAND +
            " %s"; //abilities

    @Override
    public String execute(PlayerEntity player, String command, CommandCallback callback) {
        log.info("Retrieve abilities command from client: {}", command);
        return createResponse(player);
    }

    private String createResponse(PlayerEntity player) {
        val abilityCommand = "[" +
                "%d," +   //id
                "%s," +   //name rus
                "%s," +   //name eng
                "%s," +   //type
                "%d," +   //current level
                "%d," +   //max level
                "%d," +   //damage
                "%d," +   //damage per second
                "%d," +   //interval damage
                "%d," +   //duration
                "%d," +   //cooldown
                "%d," +   //cost for the next level
                "%d" +    //study time for the next level
                "]";
        return String.format(RESPONSE_COMMAND,
                player.getAbilities().stream()
                        .map(abilityEntity -> {
                            val currentLevel = abilityEntity.getCurrentLevel();
                            val type = abilityEntity.getType();
                            return String.format(abilityCommand,
                                    abilityEntity.getId(),
                                    abilityEntity.getNameRus(),
                                    abilityEntity.getNameEng(),
                                    type,
                                    currentLevel,
                                    abilityEntity.getMaxLevel(),
                                    abilityEntity.getDamage(),
                                    abilityEntity.getDamagePerSecond(),
                                    abilityEntity.getIntervalDamage(),
                                    abilityEntity.getDuration(),
                                    abilityEntity.getCooldown(),
                                    currentLevel * type.getLevelCost(),
                                    currentLevel * type.getStudyTime());
                        })
                        .collect(Collectors.joining(" "))
        );
    }
}