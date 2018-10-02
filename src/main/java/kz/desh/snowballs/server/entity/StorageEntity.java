package kz.desh.snowballs.server.entity;

import kz.desh.snowballs.server.control.Items;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Setter
@Getter
@Entity
@Table(name = "storage")
public class StorageEntity {
    @Id
    @SequenceGenerator(name = "storage_id_seq_gen", sequenceName = "storage_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "storage_id_seq_gen")
    private long id;

    @Column(name = "snowballs")
    private int snowballs;

    @Column(name = "snowflakes")
    private int snowflakes;

    @Column(name = "icicles")
    private int icicles;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private StorageType type = StorageType.BASIC;

    @OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinTable(
            name = "storage_items",
            joinColumns = {@JoinColumn(name = "storage_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "item_id", referencedColumnName = "id")}
    )
    private Set<ItemEntity> items = Stream
            .of(Items.getItem(1L),
                    Items.getItem(2L),
                    Items.getItem(3L),
                    Items.getItem(4L),
                    Items.getItem(5L))
            .collect(Collectors.toSet());

    public void addSnowball(int snowballs) {
        this.snowballs += snowballs;
    }

    public void addSnowflakes(int snowflakes) {
        this.snowflakes += snowflakes;
    }

    public void minusSnowflakes(int snowflakes) {
        this.snowflakes -= snowflakes;
    }

    public void addIcicles(int icicles) {
        this.icicles += icicles;
    }
}