package com.essers.wms.movement.data.service;

import com.essers.wms.movement.data.entity.Damagereport;
import com.essers.wms.movement.data.repo.DamagereportRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DamagereportServ {
    @Autowired
    private DamagereportRepo damagereportRepo;

    public void savereport(Damagereport damagereport){
        damagereportRepo.save(damagereport);
    }

    public List<Damagereport> getAll() {
        return damagereportRepo.findAll();
    }
}
