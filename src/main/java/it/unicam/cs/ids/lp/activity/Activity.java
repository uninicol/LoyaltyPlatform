package it.unicam.cs.ids.lp.activity;

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
public class Activity {
    @Id
    private String name;
    private String address;
    private String telephoneNumber;
    private String email;
    @Enumerated(EnumType.STRING)
    private ContentCategory category;
    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Activity activity = (Activity) o;
        return name != null && Objects.equals(name, activity.name);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public enum ContentCategory {
        TECH,
        LIFESTYLE,
        FITNESS,
    }
}
