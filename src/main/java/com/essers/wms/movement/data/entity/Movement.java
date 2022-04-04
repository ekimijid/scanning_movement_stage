package com.essers.wms.movement.data.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
//@Transactional
public class Movement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movement_ID;
    private String wms_company;
    private String wms_site;
    private String wms_warehouse;
    private Movementtype movement_type;
    private String priority;
    private String product_ID;
    private String supplier_ID;
    private Integer quantity;
    private String uom;
    private String location_from;
    private String location_to;
    private LocalDate in_progress_timestamp;
    private String in_progress_user;
    private String location;
    private String state;
    @ManyToOne
    @JoinColumn(name = "picking_list_ID")
    private Pickinglist pickinglist;

    @OneToOne
    @JoinColumn(name = "stock_ID")
    private Stock stock;

    public Long getMovement_ID() {
        return movement_ID;
    }

    public void setMovement_ID(Long movement_ID) {
        this.movement_ID = movement_ID;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWms_company() {
        return wms_company;
    }

    public void setWms_company(String wms_company) {
        this.wms_company = wms_company;
    }

    public String getWms_site() {
        return wms_site;
    }

    public void setWms_site(String wms_site) {
        this.wms_site = wms_site;
    }

    public String getWms_warehouse() {
        return wms_warehouse;
    }

    public void setWms_warehouse(String wms_warehouse) {
        this.wms_warehouse = wms_warehouse;
    }

    public Movementtype getMovement_type() {
        return movement_type;
    }

    public void setMovement_type(Movementtype movement_type) {
        this.movement_type = movement_type;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getProduct_ID() {
        return product_ID;
    }

    public void setProduct_ID(String product_ID) {
        this.product_ID = product_ID;
    }

    public String getSupplier_ID() {
        return supplier_ID;
    }

    public void setSupplier_ID(String supplier_ID) {
        this.supplier_ID = supplier_ID;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getLocation_from() {
        return location_from;
    }

    public void setLocation_from(String location_from) {
        this.location_from = location_from;
    }

    public String getLocation_to() {
        return location_to;
    }

    public void setLocation_to(String location_to) {
        this.location_to = location_to;
    }

    public LocalDate getIn_progress_timestamp() {
        return in_progress_timestamp;
    }

    public void setIn_progress_timestamp(LocalDate in_progress_timestamp) {
        this.in_progress_timestamp = in_progress_timestamp;
    }

    public String getIn_progress_user() {
        return in_progress_user;
    }

    public void setIn_progress_user(String in_progress_user) {
        this.in_progress_user = in_progress_user;
    }

    public Pickinglist getPickinglist() {
        return pickinglist;
    }

    public void setPickinglist(Pickinglist pickinglist) {
        this.pickinglist = pickinglist;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }
}