package kz.desh.snowballs.server.control.player;

import java.util.HashMap;
import java.util.Map;

public final class PlayerExperience {
    private static final Map<Integer, Integer> EXPERIENCE_FOR_NEXT_LEVEL = new HashMap<>();

    static {
        EXPERIENCE_FOR_NEXT_LEVEL.put(2, 20);
        EXPERIENCE_FOR_NEXT_LEVEL.put(3, 40);
        EXPERIENCE_FOR_NEXT_LEVEL.put(4, 80);
        EXPERIENCE_FOR_NEXT_LEVEL.put(5, 160);
        EXPERIENCE_FOR_NEXT_LEVEL.put(6, 320);
        EXPERIENCE_FOR_NEXT_LEVEL.put(7, 640);
        EXPERIENCE_FOR_NEXT_LEVEL.put(8, 1280);
        EXPERIENCE_FOR_NEXT_LEVEL.put(9, 2560);
        EXPERIENCE_FOR_NEXT_LEVEL.put(10, 5120);
    }

    private PlayerExperience() {
    }

    public static int getExperienceForNextLevel(int level) {
        return EXPERIENCE_FOR_NEXT_LEVEL.get(level);
    }
}