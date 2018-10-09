package kz.desh.snowballs.server.control.item;

import kz.desh.snowballs.server.entity.item.ItemEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class Items {
    private final ItemEntityRepository itemEntityRepository;

    private static Map<Long, ItemEntity> items = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        this.itemEntityRepository.findAll().forEach(item -> items.put(item.getId(), item));
    }

    public static Map<Long, ItemEntity> getItems() {
        return items;
    }

    public static ItemEntity getItem(Long id) {
        if (Objects.isNull(id)) {
            return null;
        } else {
            return items.get(id);
        }
    }

    public static boolean exists(Long id) {
        return items.containsKey(id);
    }
}