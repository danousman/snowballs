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

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "action_id")
    private ActionEntity actionEntity = new ActionEntity();

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "storage_id")
    private StorageEntity storageEntity = new StorageEntity();

    @OneToMany(cascade = CascadeType.PERSIST)
    private Set<SkillEntity> skills = Stream.of(
            new SkillEntity(ActionType.PRECISION),
            new SkillEntity(ActionType.STRENGTH),
            new SkillEntity(ActionType.DODGE))
            .collect(Collectors.toSet());

    public PlayerEntity(String login) {
        this.login = login;
    }

    public SkillEntity getSkill(ActionType skill) {
        return this.skills.stream().filter(it -> it.getSkill() == skill).findFirst().get();
    }
}