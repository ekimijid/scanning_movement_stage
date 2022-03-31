package com.essers.wms.movement.data.service;

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
    public Pickinglist getById(UUID Id) {
        return pickinglistRepo.getById(Id);
    }
}
