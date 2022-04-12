package com.essers.wms.movement.data.repo;

import com.essers.wms.movement.data.entity.Pickinglist;
import com.essers.wms.movement.data.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepo extends JpaRepository<Supplier, Long> {

}
