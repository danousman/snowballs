package kz.desh.snowballs.server.entity.skill;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

public enum SkillType {
    DODGE(new SkillEntity("Уклонение", "Dodge", 10), SkillLevels.DEFAULT_LEVELS),
    STRENGTH(new SkillEntity("Сила броска", "Throw strength", 10), SkillLevels.DEFAULT_LEVELS);

    private final SkillEntity skill;
    private final Map<Integer, Level> levels;

    SkillType(SkillEntity skill, Map<Integer, Level> levels) {
        this.skill = skill;
        this.levels = levels;
    }

    public int getLevelCost(int level) {
        return this.levels.get(level).getLevelCost();
    }

    public int getStudyTime(int level) {
        return this.levels.get(level).getStudyTime();
    }

    public float getBonus(int level) {
        return this.levels.get(level).getBonus();
    }

    public SkillEntity getSkill() {
        this.skill.setType(this);
        return this.skill;
    }

    @Getter
    @AllArgsConstructor
    public static class Level {
        private final float bonus;
        private final int levelCost;
        private final int studyTime;
    }
}