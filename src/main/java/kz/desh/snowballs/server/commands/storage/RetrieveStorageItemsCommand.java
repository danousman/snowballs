package kz.desh.snowballs.server.commands.storage;

import kz.desh.snowballs.server.commands.Command;
import kz.desh.snowballs.server.commands.CommandCallback;
import kz.desh.snowballs.server.control.Items;
import kz.desh.snowballs.server.entity.PlayerEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class RetrieveStorageItemsCommand implements Command {
    public static final String COMMAND = "10007";

    private static final String RESPONSE_COMMAND = COMMAND +
            " %s"; //items

    @Override
    public String execute(PlayerEntity player, String command, CommandCallback callback) {
        log.info("Retrieve storage items command from client: {}", command);
        return createResponse(player);
    }

    private String createResponse(PlayerEntity player) {
        val itemCommand = "[" +
                "%d;" +   //id
                "%s;" +   //name rus
                "%s;" +   //name eng
                "%s;" +   //type
                "%d;" +   //level
                "%d;" +   //heat
                "%.1f" +  //dodge
                "]";
        return String.format(RESPONSE_COMMAND,
                player.getStorageEntity().getItems().stream()
                        .map(Items::getItem)
                        .collect(Collectors.toSet())
                        .stream()
                        .map(itemEntity -> String.format(itemCommand,
                                itemEntity.getId(),
                                itemEntity.getNameRus(),
                                itemEntity.getNameEng(),
                                itemEntity.getType(),
                                itemEntity.getLevel(),
                                itemEntity.getHeat(),
                                itemEntity.getDodge()))
                        .collect(Collectors.joining(" "))
        );
    }
}