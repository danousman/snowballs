package kz.desh.snowballs.server.commands.storage;

import kz.desh.snowballs.server.commands.Command;
import kz.desh.snowballs.server.commands.CommandCallback;
import kz.desh.snowballs.server.entity.PlayerEntity;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
public class RetrieveStorageCommand implements Command {
    public static final String COMMAND = "10001";

    private static final String RESPONSE_COMMAND = COMMAND +
            " %d" + //snowballs
            " %d" + //snowflakes
            " %d" + //icicles
            " %s" + //storage type
            " %d";  //storage max size

    @Override
    public String execute(PlayerEntity player, String command, CommandCallback callback) {
        return createResponse(player);
    }

    private String createResponse(PlayerEntity player) {
        val storageEntity = player.getStorageEntity();
        return String.format(RESPONSE_COMMAND,
                storageEntity.getSnowballs(),
                storageEntity.getSnowflakes(),
                storageEntity.getIcicles(),
                storageEntity.getType(),
                storageEntity.getType().getSize());
    }
}