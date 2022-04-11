package com.essers.wms.movement.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@NotNull
public class Pickinglist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long picking_list_ID;

    @OneToOne
    private Company wms_company;

    @OneToMany(mappedBy = "pickinglist")
    private List<Site> wms_site;

    @OneToMany(mappedBy = "pickinglist")
    private  List<Warehouse> wms_warehouse;

    @OneToMany(mappedBy = "pickinglist")
    private List<Product> product;

    @OneToMany(mappedBy = "pickinglist")
    private List<Supplier> supplier_ID;

    private Integer quantity;
    private String uom;
    private String location;

    @OneToMany(mappedBy = "pickinglist")
    private List<Movement> movements;

    public String getCompany(Company company) {
        return company.getName();
    }

    public String getSite(Site site) {
        return site.getName();
    }

    public String getWarehouse(Warehouse warehouse) {
        return warehouse.getName();
    }

    public String getProduct(Product product) {
        return product.getName();
    }

    public String getSupplier_ID(Supplier supplier) {
        return supplier.getName();
    }
}