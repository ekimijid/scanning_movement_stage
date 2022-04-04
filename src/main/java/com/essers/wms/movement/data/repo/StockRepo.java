package com.essers.wms.movement.data.repo;

import com.essers.wms.movement.data.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepo extends JpaRepository<Stock, Long> {
    Stock getStockByLocation(String location);
}
