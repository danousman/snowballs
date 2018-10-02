package kz.desh.snowballs.server.entity.skill;

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

    @Column(name = "name_rus")
    private String nameRus;

    @Column(name = "name_eng")
    private String nameEng;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private SkillType type;

    @Column(name = "current_level")
    private int currentLevel = 1;

    @Column(name = "max_level")
    private int maxLevel;

    SkillEntity(String nameRus, String nameEng, int maxLevel) {
        this.nameRus = nameRus;
        this.nameEng = nameEng;
        this.maxLevel = maxLevel;
    }

    public boolean canStudyNewLevel() {
        return this.currentLevel < this.maxLevel;
    }

    public void increaseLevel() {
        ++this.currentLevel;
    }
}