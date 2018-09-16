package kz.desh.snowballs.server.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "skill")
@EqualsAndHashCode
@NoArgsConstructor
public class SkillEntity {
    @Id
    @SequenceGenerator(name = "skill_id_seq_gen", sequenceName = "skill_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "skill_id_seq_gen")
    private long id;

    @Column(name = "skill")
    @Enumerated(EnumType.STRING)
    private ActionType skill;

    @Column(name = "current_level")
    private int currentLevel = 1;

    SkillEntity(ActionType skill) {
        this.skill = skill;
    }
}