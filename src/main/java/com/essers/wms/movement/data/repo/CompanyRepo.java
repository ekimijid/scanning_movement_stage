package com.essers.wms.movement.data.repo;

import com.essers.wms.movement.data.entity.Company;
import com.essers.wms.movement.data.entity.Pickinglist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepo extends JpaRepository<Company, Long> {
    Company getCompanyByPickinglist(Pickinglist pickinglist);
}
