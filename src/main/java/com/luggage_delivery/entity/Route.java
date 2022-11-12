package com.luggage_delivery.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Route implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "start_point")
    private String startPoint;
    @Column(name = "destination_point")
    private String destinationPoint;
    @Column(name = "distance")
    private double distance;

    @OneToMany(mappedBy = "route")
    @JoinColumn(name = "routes_id")
    private List<Delivery> deliveries;
}
