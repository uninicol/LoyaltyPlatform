package it.unicam.cs.ids.lp.activity.card;

import it.unicam.cs.ids.lp.activity.Activity;
import it.unicam.cs.ids.lp.activity.campaign.Campaign;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;
    String name;
    @Transient
    private List<Rule<?>> rules;
    @OneToMany(mappedBy = "card", orphanRemoval = true, cascade = CascadeType.PERSIST)
    @ToString.Exclude
    private List<Activity> activities;

    @Enumerated(EnumType.STRING)
    private CardProgram program;

    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Card card = (Card) o;
        return getId() != null && Objects.equals(getId(), card.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
