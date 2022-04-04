package com.essers.wms.movement.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@NotNull
public class Pickinglist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long picking_list_ID;
    private String wms_company;
    private String wms_site;
    private String wms_warehouse;

    @OneToMany(mappedBy = "pickinglist")
    private List<Product> product;
    private String supplier_ID;
    private Integer quantity;
    private String uom;
    private String location;

    @OneToMany(mappedBy = "pickinglist", cascade = CascadeType.ALL, fetch= FetchType.EAGER)
    private List<Movement> movements;

}