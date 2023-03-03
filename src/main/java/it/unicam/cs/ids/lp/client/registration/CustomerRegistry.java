package it.unicam.cs.ids.lp.client.registration;

import it.unicam.cs.ids.lp.client.Customer;
import it.unicam.cs.ids.lp.client.card.CustomerCard;

public interface CustomerRegistry<C extends Customer, CSC extends CustomerCard> {

    /**
     * Registra il customer nel database
     *
     * @param customer il customer da registrare
     * @return true se il customer è stata registrato con successo, false altrimenti
     */
    boolean registerCustomer(C customer, CSC customerCard);

    /**
     * Disiscrive il customer nel database
     *
     * @param name il nome del customer
     */
    void unregisterCustomerByName(String name);
}
