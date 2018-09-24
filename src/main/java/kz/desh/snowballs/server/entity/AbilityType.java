package kz.desh.snowballs.server.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AbilityType {
    BIG_SNOWBALL(new AbilityEntity("Большой снежок", "Big snowball", 5, 15, 0, 0, 0), 10, 600);

    private final AbilityEntity ability;

    @Getter
    private final int levelCost;

    @Getter
    private final int studyTime;

    public AbilityEntity getAbility() {
        this.ability.setType(this);
        return this.ability;
    }
}