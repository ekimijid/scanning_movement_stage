package com.essers.wms.movement.data.repo;

import com.essers.wms.movement.data.entity.Movement;
import com.essers.wms.movement.data.entity.Pickinglist;
import com.essers.wms.movement.data.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MovementRepo extends JpaRepository<Movement, Long> {
    List<Movement> getMovementsByPickinglist(Pickinglist pickinglist);
}
