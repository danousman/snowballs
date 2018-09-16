package kz.desh.snowballs.server.control;

import kz.desh.snowballs.server.entity.ActionType;
import kz.desh.snowballs.server.entity.PlayerEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;

import java.util.*;

public final class Skills {
    private static final Map<ActionType, List<Skill>> SKILLS;

    static {
        SKILLS = new HashMap<>();
        List<Skill> skillList = new ArrayList<>();

        skillList.add(2, new Skill(50, 600)); //10 min
        skillList.add(3, new Skill(100, 1_800)); //30 min
        skillList.add(4, new Skill(150, 5_400)); //1 h 30 min

        SKILLS.put(ActionType.PRECISION, skillList);
        SKILLS.put(ActionType.STRENGTH, skillList);
        SKILLS.put(ActionType.DODGE, skillList);
    }

    public static Optional<Skill> checkSkill(PlayerEntity player, ActionType skill) {
        val nextLevel = player.getSkill(skill).getCurrentLevel() + 1;
    }

    @Getter
    @AllArgsConstructor
    public static class Skill {
        private int cost;
        private int time;
    }
}