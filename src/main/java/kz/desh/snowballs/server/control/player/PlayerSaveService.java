package kz.desh.snowballs.server.control.player;

import kz.desh.snowballs.server.entity.PlayerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PlayerSaveService {
    private final PlayerEntityRepository playerEntityRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Async
    @Transactional
    public void savePlayer(PlayerEntity playerEntity) {
        if (Objects.nonNull(playerEntity)) {
            this.playerEntityRepository.saveAndFlush(playerEntity);
            this.entityManager.detach(playerEntity);
        }
    }
}