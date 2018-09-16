package kz.desh.snowballs.server.commands;

import kz.desh.snowballs.server.control.ActionService;
import kz.desh.snowballs.server.control.Experience;
import kz.desh.snowballs.server.control.PlayerEntityRepository;
import kz.desh.snowballs.server.control.Players;
import kz.desh.snowballs.server.entity.PlayerEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginCommand implements Command {
    public static final String COMMAND = "00001";

    private static final String RESPONSE_COMMAND = COMMAND +
            " {}" + //level
            " {}" + //experience
            " {}" + //experience to next level
            " {}" + //simple snowballs
            " {}" + //middle snowballs
            " {}" + //hard snowballs
            " {}" + //storage type
            " {}";  //storage max size

    private final PlayerEntityRepository playerEntityRepository;
    private final ActionService actionService;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public String execute(Long playerId, String command, CommandCallback callback) {
        log.info("Login command from client: {}", command);
        val playerEntity = this.playerEntityRepository.findByLogin(command);
        if (playerEntity.isPresent()) {
            val player = playerEntity.get();
            rememberPlayer(player, callback);
            this.actionService.doAction(player);
            return createResponse(player);
        } else {
            val player = this.playerEntityRepository.saveAndFlush(createPlayer(command));
            rememberPlayer(player, callback);
            return createResponse(player);
        }
    }

    private void rememberPlayer(PlayerEntity player, CommandCallback callback) {
        this.entityManager.detach(player);
        Players.addPlayer(player);
        if (callback != null) {
            callback.call(player.getId());
        }
    }

    private String createResponse(PlayerEntity player) {
        val storage = player.getStorageEntity();
        return String.format(RESPONSE_COMMAND,
                player.getLevel(),
                player.getExperience(),
                Experience.EXPERIENCE_FOR_NEXT_LEVEL.get(player.getLevel()),
                storage.getSimpleSnowballs(),
                storage.getMiddleSnowballs(),
                storage.getHardSnowballs(),
                storage.getType(),
                storage.getType().getSize());
    }

    private PlayerEntity createPlayer(String login) {
        val player = new PlayerEntity();
        player.setLogin(login);
        return player;
    }
}