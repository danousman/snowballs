package kz.desh.snowballs.server.control;

import kz.desh.snowballs.server.entity.PlayerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
class PlayerSaveService {
    private final PlayerEntityRepository playerEntityRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Async
    @Transactional
    void savePlayer(PlayerEntity playerEntity) {
        this.playerEntityRepository.saveAndFlush(playerEntity);
        this.entityManager.detach(playerEntity);
    }
}