package kz.desh.snowballs.server.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;

import java.util.HashMap;
import java.util.Map;

public enum SkillType {
    DODGE(new SkillEntity("Уклонение", "Dodge", 10));

    private final SkillEntity skill;
    private final Map<Integer, Level> levels;

    SkillType(SkillEntity skill) {
        this.skill = skill;
        this.levels = initLevels();
    }

    private Map<Integer, Level> initLevels() {
        val levels = new HashMap<Integer, Level>();
        levels.put(1, new Level(0.5f, 0, 0));
        levels.put(2, new Level(1f, 10, 900_000));          //15 minutes
        levels.put(3, new Level(1.5f, 40, 2_700_000));      //45 minutes
        levels.put(4, new Level(2f, 160, 7_200_000));       //2 hours
        levels.put(5, new Level(2.5f, 500, 21_600_000));    //6 hours
        levels.put(6, new Level(3f, 1000, 43_200_000));     //12 hours
        levels.put(7, new Level(3.5f, 2000, 86_400_000));   //1 day
        levels.put(8, new Level(4f, 4000, 172_800_000));    //2 days
        levels.put(9, new Level(4.5f, 8000, 259_200_000));  //3 days
        levels.put(10, new Level(5f, 16000, 345_600_000));  //4 days
        return levels;
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