package kz.desh.snowballs.server.control;

import kz.desh.snowballs.server.entity.PlayerEntity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class Players {
    private Players() {
    }

    private static final Map<Long, PlayerEntity> players = new ConcurrentHashMap<>();

    public static void addPlayer(PlayerEntity playerEntity) {
        players.put(playerEntity.getId(), playerEntity);
    }
}