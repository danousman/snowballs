package kz.desh.snowballs.server.commands.item;

import kz.desh.snowballs.server.commands.Command;
import kz.desh.snowballs.server.commands.CommandCallback;
import kz.desh.snowballs.server.control.item.Items;
import kz.desh.snowballs.server.entity.PlayerEntity;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RetrieveItemsCommand implements Command {
    public static final String COMMAND = "10006";

    private static final String RESPONSE_COMMAND = COMMAND +
            " %s"; //items

    @Override
    public String execute(PlayerEntity player, String command, CommandCallback callback) {
        return createResponse();
    }

    private String createResponse() {
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
                Items.getItems().values().stream()
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