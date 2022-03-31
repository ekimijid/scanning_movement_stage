package com.essers.wms.movement.data.service;

import com.essers.wms.movement.data.entity.Movement;
import com.essers.wms.movement.data.repo.MovementRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MovementServImpl implements Movementserv{

    @Autowired
    private MovementRepo movementRepo;

    @Override
    public List<Movement> getAll() {
        return movementRepo.findAll();
    }

    @Override
    public Movement getById(UUID Id) {
        return movementRepo.getById(Id);
    }
}
