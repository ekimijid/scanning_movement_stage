package com.essers.wms.movement.data.service;

import com.essers.wms.movement.data.entity.Movement;
import com.essers.wms.movement.data.entity.Pickinglist;

import java.util.List;
import java.util.UUID;

public interface Movementserv {
    List<Movement> getAll();
    Movement getById(Long Id);
    List<Movement>getByPickinglist(Pickinglist pickinglist);
    void remove(Movement movement);

}
