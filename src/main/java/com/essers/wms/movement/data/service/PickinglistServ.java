package com.essers.wms.movement.data.service;

import com.essers.wms.movement.data.entity.Pickinglist;

import java.util.List;
import java.util.UUID;

public interface PickinglistServ {
    List<Pickinglist> getAll();
    Pickinglist getById(Long Id);
}
