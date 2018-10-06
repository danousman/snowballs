package kz.desh.snowballs.server.entity.ability;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

class AbilityLevels {
    static final Map<Integer, Level> BIG_SNOWBALL_LEVELS = new HashMap<>();

    static {
        BIG_SNOWBALL_LEVELS.put(1, new Level(0, 0, 0, 0));
        BIG_SNOWBALL_LEVELS.put(2, new Level(15, 0, 40, 2_700_000));     //45 minutes
        BIG_SNOWBALL_LEVELS.put(3, new Level(25, 0, 500, 21_600_000));   //6 hours
        BIG_SNOWBALL_LEVELS.put(4, new Level(40, 0, 2000, 86_400_000));  //1 day
        BIG_SNOWBALL_LEVELS.put(5, new Level(56, 0, 8000, 259_200_000)); //3 days
    }

    private AbilityLevels() {
    }

    @Getter
    @AllArgsConstructor
    static class Level {
        private final int damage;
        private final int damagePerSecond;
        private final int levelCost;
        private final int studyTime;
    }
}