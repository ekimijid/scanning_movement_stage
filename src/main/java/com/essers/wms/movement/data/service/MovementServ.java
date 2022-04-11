package com.essers.wms.movement.data.service;

import com.essers.wms.movement.data.entity.Movement;
import com.essers.wms.movement.data.entity.Pickinglist;

import java.util.List;

public interface MovementServ {
    List<Movement> getAll();
    Movement getById(Long Id);
    List<Movement>getByPickinglist(Pickinglist pickinglist);
    void remove(Movement movement);

    void save(Movement movement);
}
