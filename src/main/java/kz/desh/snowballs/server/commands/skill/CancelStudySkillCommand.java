package kz.desh.snowballs.server.commands.skill;

import kz.desh.snowballs.server.commands.Command;
import kz.desh.snowballs.server.commands.CommandCallback;
import kz.desh.snowballs.server.control.GameProperties;
import kz.desh.snowballs.server.control.PlayerSaveService;
import kz.desh.snowballs.server.entity.PlayerEntity;
import kz.desh.snowballs.server.entity.action.ActionType;
import kz.desh.snowballs.server.entity.skill.SkillType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class CancelStudySkillCommand implements Command {
    public static final String COMMAND = "00003";

    private static final String OK_STATUS = "OK";
    private static final String FAIL_STATUS = "FAIL";
    private static final String RESPONSE_COMMAND = COMMAND +
            " %s"; //status (OK, FAIL)

    private final PlayerSaveService playerSaveService;

    @Override
    public String execute(PlayerEntity player, String command, CommandCallback callback) {
        log.info("Cancel study skill command from client: {}", command);
        val currentTime = LocalDateTime.now();
        val skillType = SkillType.valueOf(command);
        val actionEntity = player.getActionEntity();
        val skillEntity = player.getSkill(actionEntity.getActionId());

        if (skillType == skillEntity.getType() &&
                currentTime.isBefore(actionEntity.getEndDate())) {
            val storageEntity = player.getStorageEntity();
            storageEntity.addSnowflakes(skillType.getLevelCost(skillEntity.getCurrentLevel() + 1));
            actionEntity.setType(ActionType.FREE);
            actionEntity.setStartDate(currentTime);
            actionEntity.setEndDate(currentTime.plus(GameProperties.timeToCreateSnowball, ChronoUnit.MILLIS));
            actionEntity.setActionId(null);
            this.playerSaveService.savePlayer(player);
            return createResponse(OK_STATUS);
        } else {
            return createResponse(FAIL_STATUS);
        }
    }

    private String createResponse(String status) {
        return String.format(RESPONSE_COMMAND, status);
    }
}