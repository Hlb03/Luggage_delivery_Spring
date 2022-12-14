package com.luggage_delivery.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Delivery implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Min(value = 5, message = "Luggage size is too small")
    @Column(name = "luggage_size")
    private double luggageSize;

    @NotNull
    @Column(name = "weight")
    private double weight;

    @NotNull
    @NotBlank
    @Pattern(regexp = "\\D+", message = "Luggage description should be with all capital letters")
    @Column(name = "luggage_type")
    private String luggageType;

    @NotNull
    @Column(name = "start_date")
    private Date startDate;

    @NotNull
    @Column(name = "delivery_date")
    private Date deliveryDate;

    @NotNull
    @NotBlank
    @Size(min = 4, max = 64, message = "Your address is to short or long")
    @Column(name = "delivery_address")
    private String address;

    @NotNull
    @Min(value = 100, message = "Price should not be lower than 100")
    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @Column(name = "delivery_status_name")
    @Enumerated(value = EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "routes_id")
    private Route route;

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "luggageSize = " + luggageSize + ", " +
                "weight = " + weight + ", " +
                "luggageType = " + luggageType + ", " +
                "startDate = " + startDate + ", " +
                "deliveryDate = " + deliveryDate + ", " +
                "address = " + address + ", " +
                "totalPrice = " + totalPrice + ", " +
                "user = " + user.getLogin() + ", status = " + deliveryStatus + ", route = "
                + route.getStartPoint() + " - " + route.getDestinationPoint() + ")";
    }
}
