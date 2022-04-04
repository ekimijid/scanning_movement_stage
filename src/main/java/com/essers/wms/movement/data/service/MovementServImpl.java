package com.essers.wms.movement.data.service;

import com.essers.wms.movement.data.entity.Movement;
import com.essers.wms.movement.data.entity.Pickinglist;
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
    public Movement getById(Long Id) {
        return movementRepo.getById(Id);
    }

    @Override
    public List<Movement> getByPickinglist(Pickinglist pickinglist) {
        return movementRepo.getMovementsByPickinglist(pickinglist);
    }

    @Override
    public void remove(Movement movement) {
        movementRepo.delete(movement);

    }
}
