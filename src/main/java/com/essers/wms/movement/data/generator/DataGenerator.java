package com.essers.wms.movement.data.generator;

import com.essers.wms.movement.data.entity.*;
import com.essers.wms.movement.data.repo.*;
import com.vaadin.exampledata.DataType;
import com.vaadin.flow.spring.annotation.SpringComponent;

import liquibase.pro.packaged.r;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.vaadin.exampledata.ExampleDataGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@SpringComponent
public class DataGenerator {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner loadData(MovementRepo movementRepo, PickinglistRepo pickinglistRepo,
                                      StockRepo stockRepo, UserRepository userRepository, RoleRepo roleRepo, ProductRepo productRepo) {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (pickinglistRepo.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;
            logger.info("Generating demo data");

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
            ExampleDataGenerator<Product> productExampleDataGenerator = new ExampleDataGenerator<>(Product.class,
                    LocalDateTime.now());
            productExampleDataGenerator.setData(Product::setProduct_ID, DataType.EAN13);
            productExampleDataGenerator.setData(Product::setName, DataType.TWO_WORDS);
            productExampleDataGenerator.setData(Product::setLocation, DataType.TWO_WORDS);


            ExampleDataGenerator<Pickinglist> pickinglistGenerator = new ExampleDataGenerator<>(Pickinglist.class,
                    LocalDateTime.now());
            pickinglistGenerator.setData(Pickinglist::setWms_company, DataType.COMPANY_NAME );
            pickinglistGenerator.setData(Pickinglist::setWms_site, DataType.COMPANY_NAME );
            pickinglistGenerator.setData(Pickinglist::setLocation, DataType.ADDRESS );
            pickinglistGenerator.setData(Pickinglist::setQuantity, DataType.NUMBER_UP_TO_100 );
            pickinglistGenerator.setData(Pickinglist::setSupplier_ID, DataType.FOOD_PRODUCT_EAN );
            pickinglistGenerator.setData(Pickinglist::setUom, DataType.WORD );
            pickinglistGenerator.setData(Pickinglist::setWms_warehouse, DataType.COMPANY_NAME );

            List<Product>products=productRepo.saveAll(productExampleDataGenerator.create(100, seed));

            List<Pickinglist> pickinglists =pickinglistGenerator.create(10, seed);
            pickinglistRepo.saveAll(pickinglists);
            for (Pickinglist plist:pickinglists
                 ) {
                plist.setProduct(new Random().ints(5, 0, productRepo.findAll().size()).mapToObj(productRepo.findAll()::get).collect(Collectors.toList()));

            }
            ExampleDataGenerator<Stock> stockExampleDataGenerator = new ExampleDataGenerator<>(Stock.class,
                    LocalDateTime.now());
            stockExampleDataGenerator.setData(Stock::setStock_ID, DataType.UUID);
            stockExampleDataGenerator.setData(Stock::setLocation, DataType.TWO_WORDS);
            stockExampleDataGenerator.setData(Stock::setQuantity, DataType.NUMBER_UP_TO_100);

            stockRepo.saveAll(stockExampleDataGenerator.create(10, seed));
            List<Stock> stockList =stockRepo.findAll();

            List<Movement>movements=new ArrayList<>();

            for (Pickinglist pl: pickinglists
                 ) {
                for (Product p:pl.getProduct()
                     ) {
                    Movement movement=new Movement();
                    movement.setMovement_type(Movementtype.FP);
                    movement.setIn_progress_timestamp(LocalDate.now());
                    //movement.setIn_progress_user(userRepository.getById(user.getId()).getUserName());
                    movement.setPickinglist(pl);
                    movement.setLocation_from(p.getLocation());
                    movement.setLocation_to(pl.getLocation());
                    movement.setLocation(pl.getLocation());
                    movement.setQuantity(pl.getQuantity());
                    movement.setUom(pl.getUom());
                    movement.setSupplier_ID(pl.getSupplier_ID());
                    movement.setWms_company(pl.getWms_company());
                    movement.setWms_site(pl.getWms_site());
                    movement.setWms_warehouse(pl.getWms_warehouse());
                    movement.setProduct_ID(p.getProduct_ID());
                    movement.setStock(stockRepo.getStockByLocation(pl.getLocation()));
                    movementRepo.save(movement);
                    movements.add(movement);
                }
                pl.setMovements(movements);
                pickinglistRepo.save(pl);
            }

            logger.info("... generating " + productRepo.findAll().size() + " products in entities...");
            logger.info("... generating " + movements.size() + " movements in entities...");
            logger.info("... generating " + pickinglists.size() + " pickinglist in entities...");
            logger.info("... generating " + stockList.size() + " stocklist in entities...");

            logger.info(movements.get(0).getPickinglist().getPicking_list_ID()+" in movemants pickingID");
          //  logger.info(movements.get(1).getStock().getStock_ID()+" in movemanets stockId");
            logger.info(u.getUserName() + " " +u.getPassword()+ " is saved");
        };
    }
}
