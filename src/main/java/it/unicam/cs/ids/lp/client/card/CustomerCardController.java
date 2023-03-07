package it.unicam.cs.ids.lp.client.card;

import it.unicam.cs.ids.lp.activity.card.CardRepository;
import it.unicam.cs.ids.lp.client.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customerCard")
public class CustomerCardController {

    @Autowired
    private CustomerCardRepository customerCardRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @PutMapping("/add/{customerId}/{cardId}")
    public ResponseEntity<?> addCustomerCard(@PathVariable Long customerId, @PathVariable long cardId) {
        saveCustomerCard(customerId, cardId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public CustomerCard createCustomerCard(long customerId, long cardId) {
        CustomerCard customerCard = new CustomerCard();
        customerCard.setActivityCard(cardRepository.getReferenceById(cardId));
        customerCard.setCustomer(customerRepository.getReferenceById(customerId));
        return customerCard;
    }

    public CustomerCard saveCustomerCard(CustomerCard customerCard) {
        return customerCardRepository.save(customerCard);
    }

    public CustomerCard saveCustomerCard(long customerId, long cardId) {
        return saveCustomerCard(createCustomerCard(customerId, cardId));
    }

    @GetMapping("/{customerId}/getCards")
    public ResponseEntity<?> getCustomerCards(@PathVariable long customerId) {
        //TODO fare test
        List<CustomerCard> customerCards = customerCardRepository.findByCustomer_Id(customerId);
        return new ResponseEntity<>(customerCards, HttpStatus.OK);
    }
}
