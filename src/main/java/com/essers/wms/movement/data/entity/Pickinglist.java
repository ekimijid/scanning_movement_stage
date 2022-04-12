package com.essers.wms.movement.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
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
    private Company company;

    @OneToOne
    private Site wms_site;

    @OneToOne
    private  Warehouse wms_warehouse;

    @OneToMany(mappedBy = "pickinglist")
    private List<Product> product = new ArrayList<>();

    @OneToOne
    private Supplier supplier_ID;

    private Integer quantity;
    private String uom;
    private String location;

    @OneToMany(mappedBy = "pickinglist")
    private List<Movement> movements;

}