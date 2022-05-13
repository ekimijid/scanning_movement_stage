package com.essers.wms.movement.data.service;

import com.essers.wms.movement.TestDataBuilder;
import com.essers.wms.movement.data.entity.Movement;
import com.essers.wms.movement.data.entity.Pickinglist;
import com.essers.wms.movement.data.repository.*;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class MovementServiceImplementationTest {
    @Autowired
    private MovementService movementService;

    @Autowired
    private MovementRepository movementRepository;

    @Autowired
    private PickinglistRepository pickinglistRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private SiteRepository siteRepository;
    @Autowired
    private WarehouseRepository warehouseRepository;

    private  TestDataBuilder testDataBuilder;
    @BeforeEach
    void setUp() {
        testDataBuilder= new TestDataBuilder();
        companyRepository.save(testDataBuilder.getCompany());
        siteRepository.save(testDataBuilder.getSite());
        warehouseRepository.save(testDataBuilder.getWarehouse());
        movementRepository.save(testDataBuilder.getMovement());
        pickinglistRepository.save(testDataBuilder.getPickinglist());
    }


    @Test
    void getAll() {
        Assert.assertEquals(362, movementService.getAll().size());
    }

    @Test
    void getById() {
        Assert.assertEquals(testDataBuilder.getMovement().getMovementId(), movementService.getById(testDataBuilder.getMovement().getMovementId()).getMovementId());
    }

    @Test
    void getByPickinglist() {

        Assert.assertEquals(10, movementService.getByPickinglist(testDataBuilder.getPickinglist()).size());

    }

    @Test
    void remove() {
        MovementService movementService=mock(MovementService.class);
        movementService.remove(testDataBuilder.getMovement());
        verify(movementService,times(1)).remove(testDataBuilder.getMovement());
    }

    @Test
    void save() {
        Movement movement  = new Movement();
        movement.setPalleteNummer("111111");
        movementService.save(movement);
        Assert.assertEquals(movement.getPalleteNummer(), movementService.getById(movement.getMovementId()).getPalleteNummer());
    }
}