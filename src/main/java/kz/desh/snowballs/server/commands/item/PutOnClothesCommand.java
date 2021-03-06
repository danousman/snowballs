package kz.desh.snowballs.server.commands.item;

import kz.desh.snowballs.server.commands.Command;
import kz.desh.snowballs.server.commands.CommandCallback;
import kz.desh.snowballs.server.control.item.Items;
import kz.desh.snowballs.server.entity.PlayerEntity;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class PutOnClothesCommand implements Command {
    public static final String COMMAND = "00006";

    private static final String RESPONSE_COMMAND = COMMAND +
            " %s"; //status (OK, FAIL)

    @Override
    public String execute(PlayerEntity player, String command, CommandCallback callback) {
        val itemId = Long.valueOf(command);
        val existedItem = Items.exists(itemId);

        if (!existedItem) {
            return createFailResponse();
        }

        val storageEntity = player.getStorageEntity();
        val itemFromStorage = storageEntity.getItem(itemId);

        if (Objects.isNull(itemFromStorage)) {
            return createFailResponse();
        }

        if (player.getLevel() >= itemFromStorage.getLevel()) {
            val itemToStorage = player.putOnClothes(itemFromStorage);
            storageEntity.addItem(itemToStorage);
            storageEntity.removeItem(itemFromStorage);
            return createSuccessResponse();
        } else {
            return createFailResponse();
        }
    }

    private String createSuccessResponse() {
        return String.format(RESPONSE_COMMAND, OK_STATUS);
    }

    private String createFailResponse() {
        return String.format(RESPONSE_COMMAND, FAIL_STATUS);
    }
}