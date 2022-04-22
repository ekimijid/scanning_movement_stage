package com.essers.wms.movement.data.service;

import com.essers.wms.movement.data.entity.*;
import com.essers.wms.movement.data.repo.MovementRepo;
import com.essers.wms.movement.data.repo.PickinglistRepo;
import com.essers.wms.movement.data.repo.StockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovementServImpl implements MovementServ {


    @Autowired
    private MovementRepo movementRepo;

    @Autowired
    private PickinglistServ pickinglistRepo;

    @Autowired
    private StockServ stockRepo;

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

    @Override
    public void save(Movement movement) {
        movementRepo.save(movement);
    }
}
