package kz.desh.snowballs.server.commands.player;

import kz.desh.snowballs.server.commands.Command;
import kz.desh.snowballs.server.commands.CommandCallback;
import kz.desh.snowballs.server.control.Items;
import kz.desh.snowballs.server.entity.PlayerEntity;
import kz.desh.snowballs.server.entity.item.ItemEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component
public class RetrievePlayerClothesCommand implements Command {
    public static final String COMMAND = "10008";

    private static final String RESPONSE_COMMAND = COMMAND +
            " %s"; //clothes

    @Override
    public String execute(PlayerEntity player, String command, CommandCallback callback) {
        log.info("Retrieve player clothes command from client: {}", command);
        return createResponse(player);
    }

    private String createResponse(PlayerEntity player) {
        return String.format(RESPONSE_COMMAND,
                player.getClothes().stream()
                        .map(Items::getItem)
                        .collect(Collectors.toSet())
                        .stream()
                        .map(ItemEntity::getId)
                        .map(Object::toString)
                        .collect(Collectors.joining(" "))
        );
    }
}