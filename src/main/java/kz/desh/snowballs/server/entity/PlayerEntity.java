package kz.desh.snowballs.server.entity;

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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    private Set<AbilityEntity> abilities = Stream.of(AbilityType.BIG_SNOWBALL.getAbility())
            .collect(Collectors.toSet());

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "player_skills",
            joinColumns = {@JoinColumn(name = "player_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "skill_id", referencedColumnName = "id")}
    )
    private Set<SkillEntity> skills = Stream
            .of(SkillType.DODGE.getSkill(), SkillType.STRENGTH.getSkill())
            .collect(Collectors.toSet());

    @OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinTable(
            name = "player_clothes",
            joinColumns = {@JoinColumn(name = "player_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "item_id", referencedColumnName = "id")}
    )
    private List<ItemEntity> clothes = new ArrayList<>();

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
        ItemEntity takeOffItem = null;
        for (int i = 0; i < this.clothes.size(); i++) {
            if (itemEntity.getType() == this.clothes.get(i).getType()) {
                takeOffItem = this.clothes.get(i);
                this.clothes.add(i, itemEntity);
            }
        }
        return takeOffItem;
    }

    public ItemEntity takeOffClothes(long itemId) {
        ItemEntity takeOffItem = null;
        for (int i = 0; i < this.clothes.size(); i++) {
            if (itemId == this.clothes.get(i).getId()) {
                takeOffItem = this.clothes.remove(i);
            }
        }
        return takeOffItem;
    }
}