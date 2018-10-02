package kz.desh.snowballs.server.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum SkillType {
    DODGE(new SkillEntity("Уклонение", "Dodge", 10), SkillType.DEFAULT_LEVELS),
    STRENGTH(new SkillEntity("Сила броска", "Throw strength", 10), SkillType.DEFAULT_LEVELS);

    private static final Map<Integer, Level> DEFAULT_LEVELS = new HashMap<>();

    private final SkillEntity skill;
    private final Map<Integer, Level> levels;

    static {
        DEFAULT_LEVELS.put(1, new Level(0.5f, 0, 0));
        DEFAULT_LEVELS.put(2, new Level(1f, 10, 900_000));          //15 minutes
        DEFAULT_LEVELS.put(3, new Level(1.5f, 40, 2_700_000));      //45 minutes
        DEFAULT_LEVELS.put(4, new Level(2f, 160, 7_200_000));       //2 hours
        DEFAULT_LEVELS.put(5, new Level(2.5f, 500, 21_600_000));    //6 hours
        DEFAULT_LEVELS.put(6, new Level(3f, 1000, 43_200_000));     //12 hours
        DEFAULT_LEVELS.put(7, new Level(3.5f, 2000, 86_400_000));   //1 day
        DEFAULT_LEVELS.put(8, new Level(4f, 4000, 172_800_000));    //2 days
        DEFAULT_LEVELS.put(9, new Level(4.5f, 8000, 259_200_000));  //3 days
        DEFAULT_LEVELS.put(10, new Level(5f, 16000, 345_600_000));  //4 days
    }

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