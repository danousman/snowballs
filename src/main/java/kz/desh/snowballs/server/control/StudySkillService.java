package kz.desh.snowballs.server.control;

import kz.desh.snowballs.server.entity.ActionType;
import kz.desh.snowballs.server.entity.PlayerEntity;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
class StudySkillService {
    private final SnowballsService snowballsService;

    void studySkill(PlayerEntity player) {
        val currentDate = LocalDateTime.now();
        val actionEntity = player.getActionEntity();

        if (currentDate.isAfter(actionEntity.getEndDate())) {
            val skillEntity = player.getSkill(actionEntity.getActionId());
            skillEntity.increaseLevel();
            actionEntity.setType(ActionType.FREE);
            actionEntity.setStartDate(actionEntity.getEndDate());
            actionEntity.setEndDate(null);
            actionEntity.setActionId(null);
            this.snowballsService.createSnowballs(player);
        }
    }
}