package kz.desh.snowballs.server.entity.skill;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public enum SkillType {
    DODGE(SkillLevels.DEFAULT_LEVELS),
    STRENGTH(SkillLevels.DEFAULT_LEVELS);

    private final Map<Integer, SkillLevels.Level> levels;

    public int getLevelCost(int level) {
        return this.levels.get(level).getLevelCost();
    }

    public int getStudyTime(int level) {
        return this.levels.get(level).getStudyTime();
    }

    public float getBonus(int level) {
        return this.levels.get(level).getBonus();
    }
}