package kz.desh.snowballs.server.commands.ability;

import kz.desh.snowballs.server.commands.Command;
import kz.desh.snowballs.server.commands.CommandCallback;
import kz.desh.snowballs.server.control.snowball.SnowballsService;
import kz.desh.snowballs.server.entity.PlayerEntity;
import kz.desh.snowballs.server.entity.ability.AbilityType;
import kz.desh.snowballs.server.entity.action.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class StudyAbilityCommand implements Command {
    public static final String COMMAND = "00004";

    private static final String OK_STATUS = "OK";
    private static final String FAIL_STATUS = "FAIL";
    private static final String RESPONSE_COMMAND = COMMAND +
            " %s" + //status (OK, FAIL)
            " %s" + //start date
            " %s";  //end date

    private final SnowballsService snowballsService;

    @Override
    public String execute(PlayerEntity player, String command, CommandCallback callback) {
        log.info("Ability study command from client: {}", command);
        val abilityType = AbilityType.valueOf(command);
        val abilityEntity = player.getAbility(abilityType);
        val storageEntity = player.getStorageEntity();
        val actionEntity = player.getActionEntity();
        int nextLevel = abilityEntity.getCurrentLevel() + 1;
        val needSnowflakes = abilityType.getLevelCost(nextLevel);
        val enoughSnowflakes = needSnowflakes < storageEntity.getSnowflakes();

        if (abilityEntity.canStudyNewLevel() &&
                enoughSnowflakes &&
                actionEntity.getType() == ActionType.FREE) {
            this.snowballsService.createSnowballs(player);
            val startDate = LocalDateTime.now();
            val endDate = startDate.plus(abilityType.getStudyTime(nextLevel), ChronoUnit.MILLIS);
            actionEntity.setType(ActionType.STUDY_ABILITY);
            actionEntity.setActionId(abilityEntity.getId());
            actionEntity.setStartDate(startDate);
            actionEntity.setEndDate(endDate);
            storageEntity.minusSnowflakes(needSnowflakes);
            return createSuccessResponse(startDate, endDate);
        } else {
            return createFailResponse();
        }
    }

    private String createSuccessResponse(LocalDateTime startDate, LocalDateTime endDate) {
        return String.format(RESPONSE_COMMAND,
                OK_STATUS,
                startDate.toString(),
                endDate.toString());
    }

    private String createFailResponse() {
        return String.format(RESPONSE_COMMAND,
                FAIL_STATUS,
                "",
                "");
    }
}