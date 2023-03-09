package it.unicam.cs.ids.lp.client.card;

import it.unicam.cs.ids.lp.activity.card.Card;
import it.unicam.cs.ids.lp.client.Customer;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
public class CustomerCardIds implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;
    @ManyToOne
    @JoinColumn
    private Customer customer;
    @ManyToOne
    @JoinColumn
    private Card card;
}
