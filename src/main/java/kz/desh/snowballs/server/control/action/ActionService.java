package kz.desh.snowballs.server.control.action;

import kz.desh.snowballs.server.control.ability.StudyAbilityService;
import kz.desh.snowballs.server.control.skill.StudySkillService;
import kz.desh.snowballs.server.control.snowball.SnowballsService;
import kz.desh.snowballs.server.entity.PlayerEntity;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActionService {
    private final SnowballsService snowballsService;
    private final StudySkillService studySkillService;
    private final StudyAbilityService studyAbilityService;

    public void doAction(PlayerEntity player) {
        val action = player.getActionEntity();

        switch (action.getType()) {
            case STUDY_SKILL:
                this.studySkillService.studySkill(player);
                break;
            case STUDY_ABILITY:
                this.studyAbilityService.studyAbility(player);
                break;
            default:
                this.snowballsService.createSnowballs(player);
                break;
        }
    }
}