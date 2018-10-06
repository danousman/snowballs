package kz.desh.snowballs.server.entity.storage;

import kz.desh.snowballs.server.control.Items;
import kz.desh.snowballs.server.entity.item.ItemEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "storage_items", joinColumns = @JoinColumn(name = "storage_id"))
    @Column(name = "item_id")
    private Set<Long> items = Stream
            .of(1L, 2L, 3L, 4L, 5L)
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

    public ItemEntity getItem(long itemId) {
        return Items.getItem(this.items.stream()
                .filter(item -> item == itemId)
                .findFirst()
                .orElse(null));
    }

    public void addItem(ItemEntity item) {
        if (!Objects.isNull(item)) {
            this.items.add(item.getId());
        }
    }

    public void removeItem(ItemEntity item) {
        this.items.removeIf(it -> it == item.getId());
    }
}