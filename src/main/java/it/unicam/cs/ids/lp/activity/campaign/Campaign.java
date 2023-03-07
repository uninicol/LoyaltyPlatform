package it.unicam.cs.ids.lp.activity.campaign;

import it.unicam.cs.ids.lp.activity.Activity;
import it.unicam.cs.ids.lp.activity.card.Card;
import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card activityCard;
    private String description;
    private String shortDescription;
    private String shopUrl;
    @Enumerated(EnumType.STRING)
    private Activity.ContentCategory category;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Campaign campaign = (Campaign) o;
        return getActivityCard() != null && Objects.equals(getActivityCard(), campaign.getActivityCard());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
