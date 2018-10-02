package kz.desh.snowballs.server.commands.action;

import kz.desh.snowballs.server.commands.Command;
import kz.desh.snowballs.server.commands.CommandCallback;
import kz.desh.snowballs.server.entity.PlayerEntity;
import kz.desh.snowballs.server.entity.action.ActionType;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RetrieveActionCommand implements Command {
    public static final String COMMAND = "10003";

    private static final String RESPONSE_COMMAND = COMMAND +
            " %s" + //type
            " %s" + //action
            " %s" + //start date
            " %s";  //end date

    @Override
    public String execute(PlayerEntity player, String command, CommandCallback callback) {
        log.info("Retrieve action command from client: {}", command);
        return createResponse(player);
    }

    private String createResponse(PlayerEntity player) {
        val actionEntity = player.getActionEntity();
        val type = actionEntity.getType();
        return String.format(RESPONSE_COMMAND,
                type,
                retrieveAction(type, actionEntity.getActionId(), player),
                actionEntity.getStartDate().toString(),
                actionEntity.getEndDate().toString());
    }

    private String retrieveAction(ActionType actionType, Long actionId, PlayerEntity player) {
        switch (actionType) {
            case STUDY_SKILL:
                return player.getSkill(actionId).getType().toString();
            default:
                return ActionType.FREE.toString();
        }
    }
}