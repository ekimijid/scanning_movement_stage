package com.essers.wms.movement.data.service;

import com.essers.wms.movement.data.entity.Damagereport;
import com.essers.wms.movement.data.repository.DamagereportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DamageReportService {
    @Autowired
    private DamagereportRepository damagereportRepository;

    public void saveReport(Damagereport damagereport) {
        damagereportRepository.save(damagereport);
    }

    public List<Damagereport> getAll() {
        return damagereportRepository.findAll();
    }
}
