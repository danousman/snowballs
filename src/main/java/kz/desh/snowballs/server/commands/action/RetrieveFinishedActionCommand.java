package kz.desh.snowballs.server.commands.action;

import kz.desh.snowballs.server.commands.Command;
import kz.desh.snowballs.server.commands.CommandCallback;
import kz.desh.snowballs.server.entity.PlayerEntity;
import kz.desh.snowballs.server.entity.action.ActionType;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
public class RetrieveFinishedActionCommand implements Command {
    public static final String COMMAND = "10005";

    private static final String RESPONSE_COMMAND = COMMAND +
            " %s" + //type
            " %s";  //action

    @Override
    public String execute(PlayerEntity player, String command, CommandCallback callback) {
        return createResponse(player);
    }

    private String createResponse(PlayerEntity player) {
        val finishedAction = player.getFinishedAction();
        if (Objects.isNull(finishedAction)) {
            return String.format(RESPONSE_COMMAND,
                    ActionType.FREE.toString(),
                    ActionType.FREE.toString());
        } else {
            Map.Entry<ActionType, String> actionTypeStringEntry = finishedAction.entrySet().stream().findFirst().get();
            val response = String.format(RESPONSE_COMMAND,
                    actionTypeStringEntry.getKey(),
                    actionTypeStringEntry.getValue());
            player.removeFinishedAction();
            return response;
        }
    }
}