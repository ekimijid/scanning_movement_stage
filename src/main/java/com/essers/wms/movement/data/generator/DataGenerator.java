package com.essers.wms.movement.data.generator;

import com.essers.wms.movement.data.entity.*;
import com.essers.wms.movement.data.repository.*;
import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@SpringComponent
public class DataGenerator {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    private final Random random = new Random();


    @Bean
    public CommandLineRunner loadData(MovementRepository movementRepository, PickinglistRepository pickinglistRepository, StockRepository stockRepository, ProductRepository productRepository, CompanyRepository companyRepository, WarehouseRepository warehouseRepository, SiteRepository siteRepository) {
        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            int seed = 123;
            logger.info("Generating demo data");

            List<Role> roles = new ArrayList<>();
            Role role = new Role();
            role.setName("Admin");
            Role role1 = new Role();
            role.setName("User");
            roles.add(role);
            roles.add(role1);
            roleRepository.saveAll(roles);
            User user = new User();
            user.setUserName("eki");
            user.setPassword(passwordEncoder.encode("user"));
            user.setRoles(roles);
            userRepository.save(user);
            User user2 = new User();
            user.setUserName("mijenk");
            user.setPassword(passwordEncoder.encode("user"));
            user.setRoles(roles);
            userRepository.save(user2);
            ExampleDataGenerator<Product> productExampleDataGenerator = new ExampleDataGenerator<>(Product.class, LocalDateTime.now());
            productExampleDataGenerator.setData(Product::setProductId, DataType.EAN13);
            productExampleDataGenerator.setData(Product::setName, DataType.WORD);
            productExampleDataGenerator.setData(Product::setLocation, DataType.WORD);
            productExampleDataGenerator.setData(Product::setDescription, DataType.SENTENCE);
            List<Product> products = productRepository.saveAll(productExampleDataGenerator.create(100, seed));

            ExampleDataGenerator<Company> companyExampleDataGenerator = new ExampleDataGenerator<>(Company.class, LocalDateTime.now());
            companyExampleDataGenerator.setData(Company::setName, DataType.COMPANY_NAME);
            List<Company> companies = companyRepository.saveAll(companyExampleDataGenerator.create(50, seed));

            List<Warehouse> warehouses = warehouseRepository.saveAll(Stream.of("WH10", "WH11", "WH12").map(Warehouse::new).toList());

            List<Site> sites = siteRepository.saveAll(Stream.of("Winterslag", "Genk").map(Site::new).toList());

            ExampleDataGenerator<Supplier> supplierExampleDataGenerator = new ExampleDataGenerator<>(Supplier.class, LocalDateTime.now());
            supplierExampleDataGenerator.setData(Supplier::setName, DataType.WORD);
            ExampleDataGenerator<Pickinglist> pickinglistGenerator = new ExampleDataGenerator<>(Pickinglist.class, LocalDateTime.now());
            pickinglistGenerator.setData(Pickinglist::setQuantity, DataType.NUMBER_UP_TO_10);
            pickinglistGenerator.setData(Pickinglist::setUom, DataType.WORD);
            List<Pickinglist> pickinglists = pickinglistGenerator.create(40, seed).stream().map(plist -> {
                plist.setProduct(random.ints(9, 0, products.size()).mapToObj(products::get).toList());
                plist.setCompany(companies.get(random.nextInt(companyRepository.findAll().size())));
                plist.setWmsSite(sites.get(random.nextInt(siteRepository.findAll().size())));
                plist.setWmsWarehouse(warehouses.get(random.nextInt(warehouseRepository.findAll().size())));
                plist.setLocation("RETARUS123456789");
                return plist;
            }).toList();

            List<Movement> movements = new ArrayList<>();
            List<Stock> stocks = new ArrayList<>();
            for (int i=0; i<pickinglists.size(); i++ ) {
                Stock s = new Stock();
                Pickinglist pl=pickinglists.get(i);
                s.setLocation(pl.getLocation());
                s.setQuantity(2);
                for (Product p : pl.getProduct()) {
                    s.setProductId(p.getproductId());
                    stocks.add(s);
                    Movement movement = new Movement();
                    movement.setMovementType(Movementtype.FP);
                    movement.setInProgressTimestamp(LocalDateTime.now());
                    movement.setWmsWarehouse(pl.getWmsWarehouse().getName());
                    movement.setWmsSite(pl.getWmsSite().getName());
                    movement.setWmsCompany(pl.getCompany().getName());
                    movement.setPickinglist(pl);
                    movement.setLocationFrom(p.getLocation());
                    movement.setLocationTo(pl.getLocation());
                    movement.setLocation(pl.getLocation());
                    movement.setQuantity(pl.getQuantity());
                    movement.setUom(pl.getUom());
                    movement.setWmsCompany(pl.getCompany().getName());
                    movement.setProductId(p.getproductId());
                    movement.setState("pick");
                    movement.setPalleteNummer("03600029145");
                    movements.add(movement);
                }
                pl.setMovements(movements);
            }


            for (Movement m : movements) {
                for (Stock s : stockRepository.findAll()) {
                    if (m.getProductId().equals(s.getProductId())) {
                        m.getStock().add(s);
                    }
                    movementRepository.save(m);
                }
            }
            pickinglistRepository.saveAll(pickinglists);
            stockRepository.saveAll(stocks);
            movementRepository.saveAll(movements);
        };
    }
}
