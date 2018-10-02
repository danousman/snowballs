package kz.desh.snowballs.server.entity.ability;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AbilityType {
    BIG_SNOWBALL(new AbilityEntity("Большой снежок", "Big snowball", 5, 15, 0, 0, 0, 3), 10, 600_000, 10);

    private final AbilityEntity ability;

    @Getter
    private final int levelCost;

    @Getter
    private final int studyTime;

    @Getter
    private final int damagePerLevel;

    public AbilityEntity getAbility() {
        this.ability.setType(this);
        return this.ability;
    }
}