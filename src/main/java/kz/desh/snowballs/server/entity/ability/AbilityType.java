package kz.desh.snowballs.server.entity.ability;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public enum AbilityType {
    BIG_SNOWBALL(AbilityLevels.BIG_SNOWBALL_LEVELS);

    private final Map<Integer, AbilityLevels.Level> levels;

    public int getDamage(int level) {
        return this.levels.get(level).getDamage();
    }

    public int getLevelCost(int level) {
        return this.levels.get(level).getLevelCost();
    }

    public int getStudyTime(int level) {
        return this.levels.get(level).getStudyTime();
    }
}