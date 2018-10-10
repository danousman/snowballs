package kz.desh.snowballs.server.commands.item;

import kz.desh.snowballs.server.commands.Command;
import kz.desh.snowballs.server.commands.CommandCallback;
import kz.desh.snowballs.server.control.item.Items;
import kz.desh.snowballs.server.entity.PlayerEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class TakeOffClothesCommand implements Command {
    public static final String COMMAND = "00007";

    private static final String RESPONSE_COMMAND = COMMAND +
            " %s"; //status (OK, FAIL)

    @Override
    public String execute(PlayerEntity player, String command, CommandCallback callback) {
        log.info("Take off clothes command from client: {}", command);
        val itemId = Long.valueOf(command);
        val existedItem = Items.getItem(itemId);

        if (Objects.isNull(existedItem)) {
            return createFailResponse();
        } else {
            val takeOffItem = player.takeOffClothes(itemId);

            if (Objects.isNull(takeOffItem)) {
                return createFailResponse();
            }

            player.getStorageEntity().addItem(takeOffItem);
            return createSuccessResponse();
        }
    }

    private String createSuccessResponse() {
        return String.format(RESPONSE_COMMAND, OK_STATUS);
    }

    private String createFailResponse() {
        return String.format(RESPONSE_COMMAND, FAIL_STATUS);
    }
}