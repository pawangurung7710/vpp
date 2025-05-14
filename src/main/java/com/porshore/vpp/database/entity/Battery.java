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
    private Integer postcode;

    @Column(name = "watt_capacity", nullable = false)
    private Long wattCapacity;

    @Column(name = "created_at", length = 40)
    private Long  createdAt;

    @Column(name = "updated_at", length = 40)
    private Long updatedAt;

}

