package kz.desh.snowballs.server.entity.item;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "item")
@EqualsAndHashCode
@NoArgsConstructor
public class ItemEntity {
    @Id
    private long id;

    @Column(name = "name_rus")
    private String nameRus;

    @Column(name = "name_eng")
    private String nameEng;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ItemType type;

    @Column(name = "level")
    private int level;

    @Column(name = "heat")
    private int heat;

    @Column(name = "dodge")
    private float dodge;
}