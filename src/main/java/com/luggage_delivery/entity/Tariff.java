package com.luggage_delivery.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tariff implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @NotBlank(message = "Tariff type could not be blank")
    @Size(max = 45, message = "Tariff type could not be longer than 45 symbols")
    @Pattern(regexp = "[\\D ]+")
    @Column(name = "type")
    private String type;

    @NotNull(message = "Tariff price could not be null")
    @Column(name = "price")
    private BigDecimal price;

    @Override
    public String toString() {
        return "Tariff{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tariff tariff = (Tariff) o;
        return id == tariff.id && Objects.equals(type, tariff.type) && Objects.equals(price, tariff.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, price);
    }
}
