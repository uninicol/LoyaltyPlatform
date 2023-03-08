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
    public ResponseEntity<String> addCustomerCard(@PathVariable Long customerId, @PathVariable Long cardId) {
        CustomerCard customerCard = new CustomerCard();
        customerCard.setCard(cardRepository.findById(cardId).orElseThrow());
        customerCard.setCustomer(customerRepository.findById(customerId).orElseThrow());
        customerCardRepository.save(customerCard);
        return ResponseEntity.ok()
                .body("Carta aggiunta con successo");
    }

    @GetMapping("/{customerId}/getCards")
    public ResponseEntity<List<CustomerCard>> getCustomerCards(@PathVariable long customerId) {
        // TODO fare test
        List<CustomerCard> customerCards = customerCardRepository.findByCustomer_Id(customerId);
        return new ResponseEntity<>(customerCards, HttpStatus.OK);
    }
}
