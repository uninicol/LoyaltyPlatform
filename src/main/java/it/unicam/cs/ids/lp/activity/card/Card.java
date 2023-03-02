package it.unicam.cs.ids.lp.activity.card;

import it.unicam.cs.ids.lp.activity.Activity;
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
    private Integer id;
    @Transient
    private List<Rule<?>> rules;
    @OneToMany(mappedBy = "card", orphanRemoval = true)
    private List<Activity> activities;

    @Enumerated(EnumType.STRING)
    private CardProgram program;

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

    public enum CardProgram {
        Points,
        Levels,
        Membership,
        Cashback,
        Coalition,
    }
}
