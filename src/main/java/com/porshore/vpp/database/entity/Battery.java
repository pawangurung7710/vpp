package com.porshore.vpp.database.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * @author <Pawan Gurung>
 */
@Entity
@Table(name = "batteries")
@Data
public class Battery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "postcode", nullable = false, length = 20)
    private String postcode;

    @Column(name = "watt_capacity", nullable = false)
    private int wattCapacity;

    @Column(name = "CREATED_AT", length = 40)
    private String createdAt;

    @Column(name = "UPDATED_AT", length = 40)
    private String updatedAt;


}

