package kz.desh.snowballs.server.control.battle;

import kz.desh.snowballs.server.control.GameProperties;
import kz.desh.snowballs.server.entity.PlayerEntity;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.*;

public final class BattleQueue {
    private static final List<QueuePosition> QUEUE = new ArrayList<>();

    private static final int MINIMUM_BATTLE_PLAYER = 2;
    private static final int NEXT_PAIR = 1;

    private BattleQueue() {
    }

    public static synchronized void addPlayer(PlayerEntity playerEntity) {
        QUEUE.add(new QueuePosition(playerEntity.getRating(), playerEntity.getId()));
    }

    public static synchronized void removePlayer(PlayerEntity playerEntity) {
        QUEUE.removeIf(it -> it.playerId == playerEntity.getId());
    }

    public static synchronized Map<Long, Long> getBattlePairs() {
        val battlePairs = new HashMap<Long, Long>();

        QUEUE.sort(Comparator.comparing(p -> p.rating));

        if (QUEUE.size() >= MINIMUM_BATTLE_PLAYER) {
            for (int i = 0; i < QUEUE.size() - 1; i++) {
                QueuePosition player1 = QUEUE.get(i);
                QueuePosition player2 = QUEUE.get(i + 1);

                if (comparePlayerRating(player1, player2)) {
                    long player1Id = player1.playerId;
                    long player2Id = player2.playerId;

                    battlePairs.put(player1Id, player2Id);
                    i += NEXT_PAIR;
                }
            }
        }

        removePositions(battlePairs);
        return battlePairs;
    }

    private static boolean comparePlayerRating(QueuePosition player1, QueuePosition player2) {
        if (player1.rating.equals(player2.rating)) {
            return true;
        }

        return player1.rating + GameProperties.SEARCH_RATING >= player2.rating;
    }

    private static void removePositions(Map<Long, Long> battlePairs) {
        if (Objects.nonNull(battlePairs) && battlePairs.size() != 0) {
            val listToDelete = new ArrayList<Long>();
            battlePairs.forEach((k, v) -> {
                listToDelete.add(k);
                listToDelete.add(v);
            });

            QUEUE.removeIf(queuePosition -> listToDelete.contains(queuePosition.playerId));
        }
    }

    @RequiredArgsConstructor
    private static class QueuePosition {
        private final Integer rating;
        private final long playerId;
    }
}