package com.essers.wms.movement.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@NotNull
public class Pickinglist {
    @Id
    private UUID picking_list_ID;
    private String wms_company;
    private String wms_site;
    private String wms_warehouse;
    private String product_ID;
    private String supplier_ID;
    private Integer quantity;
    private Integer uom;
    private String location;
    @OneToOne(fetch = FetchType.EAGER)
    private Movement movement;



}
