package kz.desh.snowballs.server.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AbilityType {
    BIG_SNOWBALL(new AbilityEntity("Большой снежок", "Big snowball", 5, 15, 0, 0, 0));

    private final AbilityEntity ability;

    public AbilityEntity getAbility() {
        this.ability.setType(this);
        return ability;
    }
}