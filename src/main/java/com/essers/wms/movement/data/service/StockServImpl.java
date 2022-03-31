package com.essers.wms.movement.data.service;

import com.essers.wms.movement.data.entity.Stock;
import com.essers.wms.movement.data.repo.StockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServImpl implements StockServ{

    @Autowired
    private StockRepo stockRepo;
    @Override
    public List<Stock> getAll() {
        return stockRepo.findAll();
    }

    @Override
    public Stock getById(Long Id) {
        return stockRepo.getById(Id);
    }
}
