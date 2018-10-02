package kz.desh.snowballs.server.control;

import kz.desh.snowballs.server.entity.ItemEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Getter
@Component
@RequiredArgsConstructor
public class Items {
    private final ItemEntityRepository itemEntityRepository;

    private Map<Long, ItemEntity> items = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        this.items = this.itemEntityRepository.findAll().stream()
                .collect(Collectors.toMap(ItemEntity::getId, item -> item));
    }

    public ItemEntity getItem(Long id) {
        return this.items.get(id);
    }
}