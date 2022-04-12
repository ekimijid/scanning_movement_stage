package com.essers.wms.movement.data.service;

import com.essers.wms.movement.data.entity.Company;
import com.essers.wms.movement.data.entity.Pickinglist;
import com.essers.wms.movement.data.repo.CompanyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServImpl implements CompanyServ {
    @Autowired
    CompanyRepo companyRepo;

    @Override
    public List<Company> getAll() {
        return companyRepo.findAll();
    }

    @Override
    public Company getById(Long Id) {
        return companyRepo.getById(Id);
    }


}
