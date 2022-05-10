package com.essers.wms.movement.data.service;

import com.essers.wms.movement.data.entity.Product;
import com.essers.wms.movement.data.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImplementation implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Override
    public Product getByID(String id) {
        return productRepository.findProductByProductId(id);
    }
}
