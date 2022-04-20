package com.essers.wms.movement.data.service;

import com.essers.wms.movement.data.entity.Company;
import com.essers.wms.movement.data.entity.Pickinglist;

import java.util.List;
import java.util.UUID;

public interface PickinglistServ {
    List<Pickinglist> getAll();
    Pickinglist getById(Long Id);
    List<Pickinglist> getByCompany(Company company);

    void save(Pickinglist pl);
}
