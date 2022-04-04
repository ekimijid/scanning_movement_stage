package com.essers.wms.movement.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@NotNull
public class Stock {
    @Id
    private UUID stock_ID;
    private Integer quantity;
    private String location;
    private String productID;

}
