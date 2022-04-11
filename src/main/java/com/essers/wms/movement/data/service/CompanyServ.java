package com.essers.wms.movement.data.service;

import com.essers.wms.movement.data.entity.Company;
import com.essers.wms.movement.data.entity.Pickinglist;

import java.util.List;

public interface CompanyServ {
    List<Company> getAll();
    Company getById(Long Id);
    Company getCompanyByPickinglist(Pickinglist pickinglist);
}
