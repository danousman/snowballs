package kz.desh.snowballs.server.commands;

import kz.desh.snowballs.server.control.SnowballsService;
import kz.desh.snowballs.server.entity.PlayerEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StudySkillCommand implements Command {
    public static final String COMMAND = "00002";

    private static final String RESPONSE_COMMAND = COMMAND +
            " %s" + //start date
            " %s";  //end date

    private final SnowballsService snowballsService;

    @Override
    public String execute(PlayerEntity player, String command, CommandCallback callback) {
        log.info("Skill study command from client: {}", command);
        this.snowballsService.createSnowballs(player);
        return null;
    }
}