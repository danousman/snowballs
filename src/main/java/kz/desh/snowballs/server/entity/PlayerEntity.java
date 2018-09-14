package kz.desh.snowballs.server.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "player")
public class PlayerEntity {
    @Id
    @SequenceGenerator(name = "player_id_seq_gen", sequenceName = "player_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "player_id_seq_gen")
    private long id;

    @Column(name = "login")
    private String login;

    @Column(name = "level")
    private int level;

    @Column(name = "experience")
    private int experience;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "action_id")
    private ActionEntity actionEntity = new ActionEntity();
}