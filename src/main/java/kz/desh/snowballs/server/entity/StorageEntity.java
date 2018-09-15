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

    @Column(name = "simple_snowballs")
    private int simpleSnowballs;

    @Column(name = "middle_snowballs")
    private int middleSnowballs;

    @Column(name = "hard_snowballs")
    private int hardSnowballs;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private StorageType type = StorageType.BASIC;

    public void addSimpleSnowball() {
        ++this.simpleSnowballs;
    }

    public void addMiddleSnowball() {
        ++this.middleSnowballs;
    }

    public void addHardSnowball() {
        ++this.hardSnowballs;
    }

    public int snowballsCount() {
        return this.simpleSnowballs + this.middleSnowballs + this.hardSnowballs;
    }
}