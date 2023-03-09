package it.unicam.cs.ids.lp.client.card;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CustomerCard {
    @Id
    private CustomerCardIds customerCardIds;
    private Integer points = 0;
    private Integer tier = 1;
    private Boolean family = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerCard that = (CustomerCard) o;
        return customerCardIds.equals(that.customerCardIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerCardIds);
    }
}
