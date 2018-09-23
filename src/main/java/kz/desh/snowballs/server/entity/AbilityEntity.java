package kz.desh.snowballs.server.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "ability")
@EqualsAndHashCode
public class AbilityEntity {
    @Id
    @SequenceGenerator(name = "ability_id_seq_gen", sequenceName = "ability_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ability_id_seq_gen")
    private long id;

    @Column(name = "name_rus")
    private String nameRus;

    @Column(name = "name_eng")
    private String nameEng;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private AbilityType type;

    @Column(name = "current_level")
    private int currentLevel = 1;

    @Column(name = "max_level")
    private int maxLevel;

    @Column(name = "damage")
    private int damage;

    @Column(name = "damage_per_second")
    private int damagePerSecond;

    @Column(name = "interval_damage")
    private int intervalDamage;

    @Column(name = "duration")
    private int duration;
}