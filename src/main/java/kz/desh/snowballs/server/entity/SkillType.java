package kz.desh.snowballs.server.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SkillType {
    DODGE(new SkillEntity("Уклонение", "Dodge", 10), 0.5f, 10, 600_000);

    private final SkillEntity skill;

    @Getter
    private final float bonus;

    @Getter
    private final int levelCost;

    @Getter
    private final int studyTime;

    public SkillEntity getSkill() {
        this.skill.setType(this);
        return this.skill;
    }
}