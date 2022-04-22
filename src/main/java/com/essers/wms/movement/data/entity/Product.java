package com.essers.wms.movement.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private String product_ID;

    @ManyToOne()
    @JoinColumn(name = "picking_list_ID")
    private Pickinglist pickinglist;
    private String name;
    private String location;
    private String description;

    public String getProduct_ID() {
        return product_ID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProduct_ID(String product_ID) {
        this.product_ID = product_ID;
    }

    public Pickinglist getPickinglist() {
        return pickinglist;
    }

    public void setPickinglist(Pickinglist pickinglist) {
        this.pickinglist = pickinglist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
