package com.essers.wms.movement.data.repo;

import com.essers.wms.movement.data.entity.Damagereport;
import com.vaadin.flow.component.html.Image;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DamagereportRepo extends JpaRepository<Damagereport, Long> {

}
