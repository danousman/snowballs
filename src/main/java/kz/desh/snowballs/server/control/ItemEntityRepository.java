package kz.desh.snowballs.server.control;

import kz.desh.snowballs.server.entity.item.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ItemEntityRepository extends JpaRepository<ItemEntity, Long> {
}