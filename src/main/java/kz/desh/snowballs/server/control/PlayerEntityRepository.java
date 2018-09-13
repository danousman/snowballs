package kz.desh.snowballs.server.control;

import kz.desh.snowballs.server.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerEntityRepository extends JpaRepository<PlayerEntity, Long> {
    Optional<PlayerEntity> findByLogin(String login);
}