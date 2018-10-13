package kz.desh.snowballs.server.commands.skill;

import kz.desh.snowballs.server.commands.Command;
import kz.desh.snowballs.server.commands.CommandCallback;
import kz.desh.snowballs.server.control.snowball.SnowballsService;
import kz.desh.snowballs.server.entity.PlayerEntity;
import kz.desh.snowballs.server.entity.action.ActionType;
import kz.desh.snowballs.server.entity.skill.SkillType;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class StudySkillCommand implements Command {
    public static final String COMMAND = "00002";

    private static final String RESPONSE_COMMAND = COMMAND +
            " %s" + //status (OK, FAIL)
            " %s" + //start date
            " %s";  //end date

    private final SnowballsService snowballsService;

    @Override
    public String execute(PlayerEntity player, String command, CommandCallback callback) {
        val skillType = SkillType.valueOf(command);
        val skillEntity = player.getSkill(skillType);
        val storageEntity = player.getStorageEntity();
        val actionEntity = player.getActionEntity();
        int studyLevel = skillEntity.getCurrentLevel() + 1;
        val needSnowflakes = skillType.getLevelCost(studyLevel);
        val enoughSnowflakes = needSnowflakes < storageEntity.getSnowflakes();

        if (skillEntity.canStudyNewLevel() &&
                enoughSnowflakes &&
                actionEntity.getType() == ActionType.FREE) {
            this.snowballsService.createSnowballs(player);
            val startDate = LocalDateTime.now();
            val endDate = startDate.plus(skillType.getStudyTime(studyLevel), ChronoUnit.MILLIS);
            actionEntity.setType(ActionType.STUDY_SKILL);
            actionEntity.setActionId(skillEntity.getId());
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