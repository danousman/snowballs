package kz.desh.snowballs.server.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
}