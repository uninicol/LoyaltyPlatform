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
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String surname;
    private String telephoneNumber;
    private String email;
    @JsonIgnore
    private String password;
    private LocalDate registrationDate;
    @OneToMany(mappedBy = "customer", orphanRemoval = true)
    @ToString.Exclude
    private Set<CustomerCard> customerCard;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

