package kz.desh.snowballs.server.commands.storage;

import kz.desh.snowballs.server.commands.Command;
import kz.desh.snowballs.server.commands.CommandCallback;
import kz.desh.snowballs.server.control.item.Items;
import kz.desh.snowballs.server.entity.PlayerEntity;
import kz.desh.snowballs.server.entity.item.ItemEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RetrieveStorageItemsCommand implements Command {
    public static final String COMMAND = "10007";

    private static final String RESPONSE_COMMAND = COMMAND +
            " %s"; //items

    @Override
    public String execute(PlayerEntity player, String command, CommandCallback callback) {
        return createResponse(player);
    }

    private String createResponse(PlayerEntity player) {
        return String.format(RESPONSE_COMMAND,
                player.getStorageEntity().getItems().stream()
                        .map(Items::getItem)
                        .collect(Collectors.toSet())
                        .stream()
                        .map(ItemEntity::getId)
                        .map(Object::toString)
                        .collect(Collectors.joining(" "))
        );
    }
}