package kz.desh.snowballs.server.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.AbstractMap;
import java.util.Collections;
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
    private Set<SkillEntity> skills = Stream.of(SkillType.DODGE.getSkill())
            .collect(Collectors.toSet());

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
}