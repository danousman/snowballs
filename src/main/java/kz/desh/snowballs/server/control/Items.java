package kz.desh.snowballs.server.control;

import kz.desh.snowballs.server.entity.ItemEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
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
        return items.get(id);
    }
}