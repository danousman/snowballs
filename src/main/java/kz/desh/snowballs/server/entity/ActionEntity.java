package kz.desh.snowballs.server.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "action")
public class ActionEntity {
    @Id
    @SequenceGenerator(name = "action_id_seq_gen", sequenceName = "action_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "action_id_seq_gen")
    private long id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ActionType type = ActionType.FREE;

    @Column(name = "action_id")
    private Long actionId;

    @Column(name = "start_date")
    private LocalDateTime startDate = LocalDateTime.now();

    @Column(name = "end_date")
    private LocalDateTime endDate;
}