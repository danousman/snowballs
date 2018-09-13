package kz.desh.snowballs.server.commands;

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

    private static final int FIRST_LEVEL = 1;
    private static final int START_EXPERIENCE = 0;
    private static final String RESPONSE_COMMAND = COMMAND + " %d %d %d";

    private final PlayerEntityRepository playerEntityRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public String execute(Long playerId, String command, CommandCallback callback) {
        log.info("Login command from client: {}", command);
        val playerEntity = this.playerEntityRepository.findByLogin(command);
        if (playerEntity.isPresent()) {
            val player = playerEntity.get();
            return rememberPlayerAndReturnResponse(player, callback);
        } else {
            val player = this.playerEntityRepository.saveAndFlush(createPlayer(command));
            return rememberPlayerAndReturnResponse(player, callback);
        }
    }

    private String rememberPlayerAndReturnResponse(PlayerEntity player, CommandCallback callback) {
        this.entityManager.detach(player);
        Players.addPlayer(player);
        if (callback != null) {
            callback.call(player.getId());
        }
        return String.format(RESPONSE_COMMAND,
                player.getLevel(),
                player.getExperience(),
                Experience.EXPERIENCE_FOR_NEXT_LEVEL.get(player.getLevel()));
    }

    private PlayerEntity createPlayer(String login) {
        val player = new PlayerEntity();
        player.setLogin(login);
        player.setLevel(FIRST_LEVEL);
        player.setExperience(START_EXPERIENCE);
        return player;
    }
}