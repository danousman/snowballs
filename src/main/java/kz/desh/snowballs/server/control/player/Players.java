package kz.desh.snowballs.server.control.player;

import kz.desh.snowballs.server.entity.PlayerEntity;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public final class Players {
    private Players() {
    }

    private static final Map<Long, PlayerEntity> players = new ConcurrentHashMap<>();

    public static void addPlayer(PlayerEntity playerEntity) {
        players.put(playerEntity.getId(), playerEntity);
    }

    public static void removePlayer(PlayerEntity playerEntity) {
        if (Objects.nonNull(playerEntity)) {
            players.remove(playerEntity.getId());
        }
    }

    public static PlayerEntity getPlayer(long playerId) {
        return players.get(playerId);
    }
}