package com.essers.wms.movement.data.repo;

import com.essers.wms.movement.data.entity.Pickinglist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PickinglistRepo extends JpaRepository<Pickinglist, UUID> {
}
