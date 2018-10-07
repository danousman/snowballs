package kz.desh.snowballs.server.entity;

import kz.desh.snowballs.server.control.Items;
import kz.desh.snowballs.server.entity.ability.AbilityEntity;
import kz.desh.snowballs.server.entity.ability.AbilityType;
import kz.desh.snowballs.server.entity.action.ActionEntity;
import kz.desh.snowballs.server.entity.action.ActionType;
import kz.desh.snowballs.server.entity.item.ItemEntity;
import kz.desh.snowballs.server.entity.skill.SkillEntity;
import kz.desh.snowballs.server.entity.skill.SkillType;
import kz.desh.snowballs.server.entity.storage.StorageEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.val;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Setter
@Getter
@Entity
@Table(name = "player")
@NoArgsConstructor
public class PlayerEntity {
    @Id
    @SequenceGenerator(name = "player_id_seq_gen", sequenceName = "player_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "player_id_seq_gen")
    private long id;

    @Column(name = "login")
    private String login;

    @Column(name = "level")
    private int level = 1;

    @Column(name = "experience")
    private int experience;

    @Column(name = "heat")
    private int heat = 50;

    @Column(name = "dodge")
    private float dodge = 30f;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "action_id")
    private ActionEntity actionEntity = new ActionEntity();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "storage_id")
    private StorageEntity storageEntity = new StorageEntity();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "player_abilities",
            joinColumns = {@JoinColumn(name = "player_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "ability_id", referencedColumnName = "id")}
    )
    private Set<AbilityEntity> abilities = Stream
            .of(
                    new AbilityEntity("Большой снежок", "Big snowball", AbilityType.BIG_SNOWBALL, 5, 15, 0, 0, 0, 3))
            .collect(Collectors.toSet());

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "player_skills",
            joinColumns = {@JoinColumn(name = "player_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "skill_id", referencedColumnName = "id")}
    )
    private Set<SkillEntity> skills = Stream
            .of(
                    new SkillEntity("Уклонение", "Dodge", SkillType.DODGE, 10),
                    new SkillEntity("Сила броска", "Throw strength", SkillType.STRENGTH, 10))
            .collect(Collectors.toSet());

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "player_clothes", joinColumns = @JoinColumn(name = "player_id"))
    @Column(name = "item_id")
    private Set<Long> clothes = new HashSet<>();

    @Transient
    private Map<ActionType, String> finishedAction;

    public PlayerEntity(String login) {
        this.login = login;
    }

    public SkillEntity getSkill(SkillType type) {
        return this.skills.stream()
                .filter(value -> value.getType() == type)
                .findFirst()
                .get();
    }

    public SkillEntity getSkill(Long skillId) {
        return this.skills.stream()
                .filter(value -> value.getId() == skillId)
                .findFirst()
                .get();
    }

    public void finishedAction(ActionType actionType, String action) {
        this.finishedAction = Collections.unmodifiableMap(Stream.of(new AbstractMap.SimpleEntry<>(actionType, action))
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue)));
    }

    public void removeFinishedAction() {
        this.finishedAction = null;
    }

    public AbilityEntity getAbility(AbilityType type) {
        return this.abilities.stream()
                .filter(value -> value.getType() == type)
                .findFirst()
                .get();
    }

    public AbilityEntity getAbility(Long abilityId) {
        return this.abilities.stream()
                .filter(value -> value.getId() == abilityId)
                .findFirst()
                .get();
    }

    public ItemEntity putOnClothes(ItemEntity itemEntity) {
        val takeOffItem = this.clothes.stream()
                .filter(it -> Items.getItem(it).getType() == itemEntity.getType())
                .findFirst()
                .orElse(null);

        if (Objects.isNull(takeOffItem)) {
            this.clothes.add(itemEntity.getId());
        } else {
            this.clothes.removeIf(it -> Items.getItem(it).getType() == itemEntity.getType());
        }

        return Items.getItem(takeOffItem);
    }

    public ItemEntity takeOffClothes(long itemId) {
        val takeOffItem = this.clothes.stream()
                .filter(it -> it == itemId)
                .findFirst()
                .orElse(null);

        if (!Objects.isNull(takeOffItem)) {
            this.clothes.removeIf(it -> it == itemId);
        }
        return Items.getItem(takeOffItem);
    }
}