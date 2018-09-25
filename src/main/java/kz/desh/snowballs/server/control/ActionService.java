package kz.desh.snowballs.server.control;

import kz.desh.snowballs.server.entity.PlayerEntity;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActionService {
    private final SnowballsService snowballsService;
    private final PlayerSaveService playerSaveService;
    private final StudySkillService studySkillService;

    public void doAction(PlayerEntity player) {
        val action = player.getActionEntity();

        switch (action.getType()) {
            case STUDY_SKILL:
                this.studySkillService.studySkill(player);
                break;
            default:
                this.snowballsService.createSnowballs(player);
                break;
        }

        this.playerSaveService.savePlayer(player);
    }
}