package kz.desh.snowballs.server.commands.battle;

import kz.desh.snowballs.server.commands.Command;
import kz.desh.snowballs.server.commands.CommandCallback;
import kz.desh.snowballs.server.control.battle.BattleQueue;
import kz.desh.snowballs.server.entity.PlayerEntity;
import org.springframework.stereotype.Component;


@Component
public class StartSearchBattleCommand implements Command {
    public static final String COMMAND = "20001";

    private static final String RESPONSE_COMMAND = COMMAND +
            " %s"; //status (OK, FAIL)

    @Override
    public String execute(PlayerEntity player, String command, CommandCallback callback) {
        BattleQueue.addPlayer(player);
        return createResponse();
    }

    private String createResponse() {
        return String.format(RESPONSE_COMMAND, OK_STATUS);
    }
}