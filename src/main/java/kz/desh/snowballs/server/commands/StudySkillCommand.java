package kz.desh.snowballs.server.commands;

import kz.desh.snowballs.server.control.PlayerSaveService;
import kz.desh.snowballs.server.control.SnowballsService;
import kz.desh.snowballs.server.entity.ActionType;
import kz.desh.snowballs.server.entity.PlayerEntity;
import kz.desh.snowballs.server.entity.SkillType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class StudySkillCommand implements Command {
    public static final String COMMAND = "00002";

    private static final String OK_STATUS = "OK";
    private static final String FAIL_STATUS = "FAIL";
    private static final String RESPONSE_COMMAND = COMMAND +
            " %s" + //status (OK, FAIL)
            " %s" + //start date
            " %s";  //end date

    private final SnowballsService snowballsService;
    private final PlayerSaveService playerSaveService;

    @Override
    public String execute(PlayerEntity player, String command, CommandCallback callback) {
        log.info("Skill study command from client: {}", command);
        val skillType = SkillType.valueOf(command);
        val skillEntity = player.getSkill(skillType);
        val storageEntity = player.getStorageEntity();
        val actionEntity = player.getActionEntity();
        val needSnowflakes = skillEntity.getCurrentLevel() * skillType.getLevelCost();
        val enoughSnowflakes = needSnowflakes < storageEntity.getSnowflakes();

        if (skillEntity.canStudyNewLevel() &&
                enoughSnowflakes &&
                actionEntity.getType() == ActionType.FREE) {
            this.snowballsService.createSnowballs(player);
            val startDate = LocalDateTime.now();
            val endDate = startDate.plusSeconds(skillEntity.getCurrentLevel() * skillType.getStudyTime());
            actionEntity.setType(ActionType.STUDY_SKILL);
            actionEntity.setActionId(skillEntity.getId());
            actionEntity.setStartDate(startDate);
            actionEntity.setEndDate(endDate);
            storageEntity.minusSnowflakes(needSnowflakes);
            this.playerSaveService.savePlayer(player);
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