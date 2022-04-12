package com.essers.wms.movement.data.service;

import com.essers.wms.movement.data.entity.Product;
import com.essers.wms.movement.data.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServImpl implements ProductServ {
    @Autowired
    ProductRepo productRepo;
    @Override
    public Product getByID(String Id) {
        return productRepo.getById(Id);
    }
}
