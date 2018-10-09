package kz.desh.snowballs.server.control.ability;

import kz.desh.snowballs.server.control.GameProperties;
import kz.desh.snowballs.server.control.snowball.SnowballsService;
import kz.desh.snowballs.server.entity.PlayerEntity;
import kz.desh.snowballs.server.entity.action.ActionType;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class StudyAbilityService {
    private final SnowballsService snowballsService;

    public void studyAbility(PlayerEntity player) {
        val currentDate = LocalDateTime.now();
        val actionEntity = player.getActionEntity();

        if (currentDate.isAfter(actionEntity.getEndDate())) {
            val abilityEntity = player.getAbility(actionEntity.getActionId());
            player.finishedAction(ActionType.STUDY_ABILITY, abilityEntity.getType().toString());
            abilityEntity.increaseLevel();
            actionEntity.setType(ActionType.FREE);
            actionEntity.setStartDate(actionEntity.getEndDate());
            actionEntity.setEndDate(actionEntity.getEndDate().plus(GameProperties.timeToCreateSnowball, ChronoUnit.MILLIS));
            actionEntity.setActionId(null);
            this.snowballsService.createSnowballs(player);
        }
    }
}