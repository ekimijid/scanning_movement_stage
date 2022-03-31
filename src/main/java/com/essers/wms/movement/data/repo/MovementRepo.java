package com.essers.wms.movement.data.repo;

import com.essers.wms.movement.data.entity.Movement;
import com.essers.wms.movement.data.entity.Pickinglist;
import com.essers.wms.movement.data.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MovementRepo extends JpaRepository<Movement, UUID> {
    Movement getMovementByStock(Stock stock);
    Movement getMovementByPickinglist(Pickinglist pickinglist);
}
