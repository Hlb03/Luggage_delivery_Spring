package com.luggage_delivery.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @NotBlank(message = "Name could not be blank")
    @Size(max = 30)
    @Pattern(regexp = "\\D[a-z- ]+", message = "Name should starts with capital letter")
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @NotBlank(message = "Last name could not be blank")
    @Size(max = 30)
    @Pattern(regexp = "\\D[a-z- ]+", message = "Last name should starts with capital letter")
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @NotBlank
    @Email(message = "Do not matches with email pattern")
    @Column(name = "login")
    private String login;

    @NotNull
    @NotBlank
    @Size(min = 5, max = 256, message = "Password should contain at least 5 symbols")
    @Column(name = "password")
    private String password;

    @NotNull
    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "role_name")
    @Enumerated(value = EnumType.STRING)
    private Role roleName;

    @OneToMany (mappedBy = "user")
    private Set<Delivery> deliveries;

    @Column(name = "status_name")
    @Enumerated(value = EnumType.STRING)
    private Status statusName;

    @Column(name = "activation_code")
    private String activationCode;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                ", role=" + roleName +
                ", status=" + statusName +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(balance, user.balance) && Objects.equals(roleName, user.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, login, password, balance, roleName);
    }
}
