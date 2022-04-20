package com.essers.wms.movement.data.service;

import com.essers.wms.movement.data.entity.*;
import com.essers.wms.movement.data.repo.MovementRepo;
import com.essers.wms.movement.data.repo.PickinglistRepo;
import com.essers.wms.movement.data.repo.StockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovementServImpl implements MovementServ {


    @Autowired
    private MovementRepo movementRepo;

    @Autowired
    private PickinglistServ pickinglistRepo;

    @Autowired
    private StockServ stockRepo;

  /*  public MovementServImpl() {
        List<Movement>movements=new ArrayList<>();
        for (Pickinglist pl: pickinglistRepo.getAll()
        ) {

            for (Product p:pl.getProduct()
            ) {

                Movement movement=new Movement();
                movement.setMovement_type(Movementtype.FP);
                movement.setIn_progress_timestamp(LocalDateTime.now());
                movement.setWms_warehouse(pl.getWms_warehouse().getName());
                movement.setWms_site(pl.getWms_site().getName());
                movement.setWms_company(pl.getCompany().getName());
                movement.setPickinglist(pl);
                movement.setLocation_from(p.getLocation());
                movement.setLocation_to(pl.getLocation());
                movement.setLocation(pl.getLocation());
                movement.setQuantity(pl.getQuantity());
                movement.setUom(pl.getUom());
                movement.setWms_company(pl.getCompany().getName());
                movement.setProduct_ID(p.getProduct_ID());
                movement.setState("pick");
                movements.add(movement);
            }
            pl.setMovements(movements);
            pickinglistRepo.save(pl);
        }
        movementRepo.saveAll(movements);

        for (Movement m:movements
        ) {
            for (Stock s:stockRepo.getAll()
            ) {
                if(m.getProduct_ID().equals(s.getProductID())){
                    m.getStock().add(s);
                }
                movementRepo.save(m);
            }
        }
    }*/

    @Override
    public List<Movement> getAll() {


        return movementRepo.findAll();
    }

    @Override
    public Movement getById(Long Id) {
        return movementRepo.getById(Id);
    }

    @Override
    public List<Movement> getByPickinglist(Pickinglist pickinglist) {
        return movementRepo.getMovementsByPickinglist(pickinglist);
    }

    @Override
    public void remove(Movement movement) {
        movementRepo.delete(movement);

    }

    @Override
    public void save(Movement movement) {
        movementRepo.save(movement);
    }
}
