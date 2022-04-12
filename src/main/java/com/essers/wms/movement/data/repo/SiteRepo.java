package com.essers.wms.movement.data.repo;

import com.essers.wms.movement.data.entity.Pickinglist;
import com.essers.wms.movement.data.entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteRepo extends JpaRepository<Site,Long> {

}
