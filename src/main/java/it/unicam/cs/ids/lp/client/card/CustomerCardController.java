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

    @PutMapping("/add/{customerName}/{cardId}")
    public ResponseEntity<?> addCustomerCard(@PathVariable String customerName, @PathVariable Integer cardId) {
        CustomerCard customerCard = new CustomerCard();
        customerCard.setCard(cardRepository.findById(cardId).orElseThrow());
        // TODO customerCard.setCustomer(customerRepository.findById(customerName).orElseThrow());
        customerCardRepository.save(customerCard);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{customerName}/getCards")
    public ResponseEntity<?> getCustomerCards(@PathVariable String customerName) {
        //TODO fare test
        List<CustomerCard> customerCards = customerCardRepository.findByCustomer_NameLike(customerName);
        return new ResponseEntity<>(customerCards, HttpStatus.OK);
        //return new ResponseEntity<>(cardRepository.findAll(cardRepository.getCustomerCards(customerName)),HttpStatus.OK);
    }
}
