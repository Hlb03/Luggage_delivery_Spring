package com.luggage_delivery.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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

    @NotNull
    @NotBlank
    @Pattern(regexp = "\\D[\\d- ]+", message = "Start point should begin with a capital letter")
    @Column(name = "start_point")
    private String startPoint;

    @NotNull
    @NotBlank
    @Pattern(regexp = "\\D[\\d- ]+", message = "Final point should begin with a capital letter")
    @Column(name = "destination_point")
    private String destinationPoint;

    @NotNull
    @Column(name = "distance")
    private double distance;

    @OneToMany(mappedBy = "route")
    private List<Delivery> deliveries;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "startPoint = " + startPoint + ", " +
                "destinationPoint = " + destinationPoint + ", " +
                "distance = " + distance + ")";
    }
}
