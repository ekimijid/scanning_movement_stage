package com.essers.wms.movement.data.repo;

import com.essers.wms.movement.data.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepo extends JpaRepository<Stock, Long> {
    List<Stock> getStocksByLocationAndProductID(String location, String productID);
}
