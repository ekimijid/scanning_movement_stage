package com.essers.wms.movement.data.generator;

import com.essers.wms.movement.data.entity.*;
import com.essers.wms.movement.data.repo.*;
import com.vaadin.exampledata.DataType;
import com.vaadin.flow.spring.annotation.SpringComponent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.vaadin.exampledata.ExampleDataGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@SpringComponent
public class DataGenerator {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner loadData(MovementRepo movementRepo, PickinglistRepo pickinglistRepo,
                                      StockRepo stockRepo, UserRepository userRepository, RoleRepo roleRepo) {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (pickinglistRepo.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;
            logger.info("Generating demo data");

            ExampleDataGenerator<Movement> movementExampleDataGenerator = new ExampleDataGenerator<>(Movement.class,
                    LocalDateTime.now());
            movementExampleDataGenerator.setData(Movement::setMovement_ID, DataType.UUID);
            movementExampleDataGenerator.setData(Movement::setWms_company, DataType.COMPANY_NAME);
            movementExampleDataGenerator.setData(Movement::setMovement_type, DataType.TWO_WORDS);
            movementExampleDataGenerator.setData(Movement::setQuantity, DataType.NUMBER_UP_TO_100);
            movementExampleDataGenerator.setData(Movement::setProduct_ID,DataType.FOOD_PRODUCT_EAN);
            movementExampleDataGenerator.setData(Movement::setUom, DataType.TWO_WORDS);
            movementExampleDataGenerator.setData(Movement::setIn_progress_timestamp, DataType.DATE_LAST_7_DAYS );
            movementExampleDataGenerator.setData(Movement::setWms_site, DataType.COMPANY_NAME);
            movementExampleDataGenerator.setData(Movement::setIn_progress_user, DataType.FIRST_NAME);
            movementExampleDataGenerator.setData(Movement::setLocation_from, DataType.TWO_WORDS);
            movementExampleDataGenerator.setData(Movement::setLocation_to, DataType.TWO_WORDS);
            movementExampleDataGenerator.setData(Movement::setPriority, DataType.TWO_WORDS);
            movementExampleDataGenerator.setData(Movement::setSupplier_ID, DataType.TWO_WORDS);
            movementExampleDataGenerator.setData(Movement::setWms_warehouse, DataType.COMPANY_NAME);


            ExampleDataGenerator<Stock> stockExampleDataGenerator = new ExampleDataGenerator<>(Stock.class,
                    LocalDateTime.now());
            stockExampleDataGenerator.setData(Stock::setStock_ID, DataType.UUID);
            stockExampleDataGenerator.setData(Stock::setLocation, DataType.TWO_WORDS);
            stockExampleDataGenerator.setData(Stock::setProduct_ID, DataType.TWO_WORDS);
            stockExampleDataGenerator.setData(Stock::setQuantity, DataType.NUMBER_UP_TO_100);


           stockRepo.saveAll(stockExampleDataGenerator.create(10, seed));
            List<Stock> stockList =stockRepo.findAll();


            ExampleDataGenerator<Pickinglist> pickinglistGenerator = new ExampleDataGenerator<>(Pickinglist.class,
                    LocalDateTime.now());
            pickinglistGenerator.setData(Pickinglist::setWms_company, DataType.COMPANY_NAME );
            pickinglistGenerator.setData(Pickinglist::setWms_site, DataType.COMPANY_NAME );
            pickinglistGenerator.setData(Pickinglist::setProduct_ID, DataType.FOOD_PRODUCT_EAN );
            pickinglistGenerator.setData(Pickinglist::setLocation, DataType.ADDRESS );
            pickinglistGenerator.setData(Pickinglist::setQuantity, DataType.NUMBER_UP_TO_100 );
            pickinglistGenerator.setData(Pickinglist::setPicking_list_ID, DataType.UUID );
            pickinglistGenerator.setData(Pickinglist::setSupplier_ID, DataType.FOOD_PRODUCT_EAN );
            pickinglistGenerator.setData(Pickinglist::setUom, DataType.NUMBER_UP_TO_10 );
            pickinglistGenerator.setData(Pickinglist::setWms_warehouse, DataType.COMPANY_NAME );

            List<Pickinglist> pickinglists =(pickinglistGenerator.create(10, seed));
            pickinglistRepo.saveAll(pickinglists);
            Random r = new Random(seed);
            List<Movement> movements= movementRepo.saveAll(movementExampleDataGenerator.create(10, seed).stream().map(movement -> {
                movement.setStock(stockList.get(r.nextInt(stockList.size())));
                movement.setPickinglist(pickinglists.get(r.nextInt(pickinglists.size())));
                return movement;
            }).collect(Collectors.toList()));
            movementRepo.saveAll(movements);
            List<Pickinglist>list=new ArrayList<>();
            int index=0;
            for (Movement m:movements
                 ) {
                Pickinglist pickinglist=pickinglists.get(index);
                logger.info(pickinglist.getPicking_list_ID().toString());
                pickinglist.setMovement(m);
                list.add(pickinglist);
                index++;
            }
            pickinglistRepo.saveAll(list);

            List<Role> roles=new ArrayList<>();
            Role role = new Role();
            role.setName("Admin");
            Role role1 = new Role();
            role.setName("User");
            roles.add(role);
            roles.add(role1);
            roleRepo.saveAll(roles);

           User user=new User();
           user.setUserName("eki");
           user.setPassword(passwordEncoder.encode("user"));
           user.setRoles(roles);
           userRepository.save(user);

            User u=userRepository.getById(user.getId());
            List<Pickinglist>pl=pickinglistRepo.findAll();

            logger.info("... generating " + movements.size() + " movements in entities...");
            logger.info("... generating " + pickinglists.size() + " pickinglist in entities...");
            logger.info("... generating " + stockList.size() + " stocklist in entities...");

            logger.info(movements.get(1).getPickinglist().getPicking_list_ID()+" in movemants pickingID");
            logger.info(movements.get(1).getStock().getStock_ID()+" in movemanets stockId");
            logger.info(u.getUserName() + " " +u.getPassword()+ " is saved");
        };
    }
}
