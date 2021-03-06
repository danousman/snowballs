package kz.desh.snowballs.server.commands.player;

import kz.desh.snowballs.server.commands.Command;
import kz.desh.snowballs.server.commands.CommandCallback;
import kz.desh.snowballs.server.control.action.ActionService;
import kz.desh.snowballs.server.control.player.PlayerEntityRepository;
import kz.desh.snowballs.server.control.player.PlayerExperience;
import kz.desh.snowballs.server.control.player.Players;
import kz.desh.snowballs.server.entity.PlayerEntity;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class LoginCommand implements Command {
    public static final String COMMAND = "00001";

    private static final String RESPONSE_COMMAND = COMMAND +
            " %d" + //level
            " %d" + //experience
            " %d";  //experience to next level

    private final PlayerEntityRepository playerEntityRepository;
    private final ActionService actionService;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public String execute(PlayerEntity player, String command, CommandCallback callback) {
        val playerEntity = this.playerEntityRepository.findByLogin(command);
        if (playerEntity.isPresent()) {
            val searchedPlayer = playerEntity.get();
            rememberPlayer(searchedPlayer, callback);
            this.actionService.doAction(searchedPlayer);
            return createResponse(searchedPlayer);
        } else {
            val newPlayer = this.playerEntityRepository.saveAndFlush(new PlayerEntity(command));
            rememberPlayer(newPlayer, callback);
            return createResponse(newPlayer);
        }
    }

    private void rememberPlayer(PlayerEntity player, CommandCallback callback) {
        this.entityManager.detach(player);
        Players.addPlayer(player);
        if (callback != null) {
            callback.call(player);
        }
    }

    private String createResponse(PlayerEntity player) {
        return String.format(RESPONSE_COMMAND,
                player.getLevel(),
                player.getExperience(),
                PlayerExperience.getExperienceForNextLevel(player.getLevel() + 1));
    }
}