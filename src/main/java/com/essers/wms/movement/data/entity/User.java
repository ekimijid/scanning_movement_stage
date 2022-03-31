package com.essers.wms.movement.data.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.FetchType.EAGER;
@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String password;
    String userName;

    @OneToMany(fetch = EAGER, mappedBy = "user")
    Collection<Role> roles = new ArrayList<>();


}