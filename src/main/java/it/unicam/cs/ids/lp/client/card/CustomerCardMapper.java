package it.unicam.cs.ids.lp.client.card;

import it.unicam.cs.ids.lp.activity.card.CardRepository;
import it.unicam.cs.ids.lp.client.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.BiFunction;

@Service
public class CustomerCardMapper implements BiFunction<String, Long, CustomerCard> {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public CustomerCard apply(String email, Long cardId) {
        CustomerCard customerCard = new CustomerCard();
        CustomerCardIds customerCardIds = new CustomerCardIds();
        customerCardIds.setCustomer(customerRepository.findByEmail(email).orElseThrow());
        customerCardIds.setCard(cardRepository.findById(cardId).orElseThrow());
        customerCard.setCustomerCardIds(customerCardIds);
        return customerCard;
    }
}
