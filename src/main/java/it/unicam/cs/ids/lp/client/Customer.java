package it.unicam.cs.ids.lp.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.unicam.cs.ids.lp.JWT_auth.Role;
import it.unicam.cs.ids.lp.client.card.CustomerCard;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Customer {
    @Id
    private long id;
    private String name;
    private String surname;
    private String telephoneNumber;
    private String email;
    @JsonIgnore
    private String password;
    private LocalDate registrationDate;
    @OneToMany(mappedBy = "id")
    private Set<CustomerCard> cards;

    @ManyToMany
    @JoinTable(name = "customer_roles",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id && name.equals(customer.name) && surname.equals(customer.surname) && telephoneNumber.equals(customer.telephoneNumber) && email.equals(customer.email) && password.equals(customer.password) && registrationDate.equals(customer.registrationDate) && cards.equals(customer.cards);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

