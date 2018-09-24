package kz.desh.snowballs.server.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SkillType {
    DODGE(new SkillEntity("Уклонение", "Dodge", 10), 10, 600);

    private final SkillEntity skill;

    @Getter
    private final int levelCost;

    @Getter
    private final int studyTime;

    public SkillEntity getSkill() {
        this.skill.setType(this);
        return this.skill;
    }
}