package it.unicam.cs.ids.lp.activity.campaign;

import it.unicam.cs.ids.lp.activity.card.Card;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Campaign {

    @Id
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;
    private String description;
    private String shortDescription;
    private String shopUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Campaign campaign = (Campaign) o;
        return getCard() != null && Objects.equals(getCard(), campaign.getCard());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
