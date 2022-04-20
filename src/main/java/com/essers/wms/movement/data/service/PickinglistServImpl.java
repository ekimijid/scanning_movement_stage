package com.essers.wms.movement.data.service;

import com.essers.wms.movement.data.entity.Company;
import com.essers.wms.movement.data.entity.Pickinglist;
import com.essers.wms.movement.data.repo.PickinglistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public class PickinglistServImpl implements PickinglistServ{

    @Autowired
    private PickinglistRepo pickinglistRepo;
    @Override
    public List<Pickinglist> getAll() {
        return pickinglistRepo.findAll();

    }

    @Override
    public Pickinglist getById(Long Id) {
        return pickinglistRepo.getById(Id);
    }

    @Override
    public List<Pickinglist> getByCompany(Company company) {
        return pickinglistRepo.getPickinglistsByCompany(company);
    }

    @Override
    public void save(Pickinglist pl) {
        pickinglistRepo.save(pl);
    }
}
