package kz.desh.snowballs.server.control;

import kz.desh.snowballs.server.entity.ActionType;
import kz.desh.snowballs.server.entity.PlayerEntity;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActionService {
    private final SnowballsService snowballsService;
    private final PlayerSaveService playerSaveService;

    public void doAction(PlayerEntity player) {
        val action = player.getActionEntity();

        if (action.getType() == ActionType.FREE) {
            this.snowballsService.createSnowballs(player);
        }

        this.playerSaveService.savePlayer(player);
    }
}