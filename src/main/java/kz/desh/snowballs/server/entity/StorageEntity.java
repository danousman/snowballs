package kz.desh.snowballs.server.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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

    public void addSnowball(int snowballs) {
        this.snowballs += snowballs;
    }

    public void addSnowflakes(int snowflakes) {
        this.snowflakes += snowflakes;
    }

    public void addIcicles(int icicles) {
        this.icicles += icicles;
    }
}