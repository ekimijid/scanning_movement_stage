package com.essers.wms.movement.data.service;

import com.essers.wms.movement.data.entity.Movement;

import java.util.List;
import java.util.UUID;

public interface Movementserv {
    List<Movement> getAll();
    Movement getById(UUID Id);

}
