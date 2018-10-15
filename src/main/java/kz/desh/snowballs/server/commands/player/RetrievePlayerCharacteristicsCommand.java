package kz.desh.snowballs.server.commands.player;

import kz.desh.snowballs.server.commands.Command;
import kz.desh.snowballs.server.commands.CommandCallback;
import kz.desh.snowballs.server.entity.PlayerEntity;
import org.springframework.stereotype.Component;

@Component
public class RetrievePlayerCharacteristicsCommand implements Command {
    public static final String COMMAND = "10009";

    private static final String RESPONSE_COMMAND = COMMAND +
            " %d" +      //heat
            " %.2f" +    //dodge
            " %.2f";     //strength

    @Override
    public String execute(PlayerEntity player, String command, CommandCallback callback) {
        return createResponse(player);
    }

    private String createResponse(PlayerEntity player) {
        return String.format(RESPONSE_COMMAND,
                player.getAllHeat(),
                player.getAllDodge(),
                player.getAllStrength());
    }
}