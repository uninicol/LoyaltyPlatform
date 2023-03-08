package it.unicam.cs.ids.lp.client.card;

import it.unicam.cs.ids.lp.JWT_auth.JwtUtils;
import it.unicam.cs.ids.lp.activity.card.CardRepository;
import it.unicam.cs.ids.lp.client.CustomerRepository;
import jakarta.servlet.http.HttpServletRequest;
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
    @Autowired
    private JwtUtils jwtUtils;

    @PutMapping("/addCard/{cardId}")
    public ResponseEntity<String> addCustomerCard(HttpServletRequest request, @PathVariable Long cardId) {
        String email = jwtUtils.getEmailFromRequest(request);
        CustomerCard customerCard = new CustomerCard();
        customerCard.setCard(cardRepository.findById(cardId).orElseThrow());
        customerCard.setCustomer(customerRepository.findByEmail(email).orElseThrow());
        customerCardRepository.save(customerCard);
        return ResponseEntity.ok()
                .body("Carta aggiunta con successo");
    }

    @GetMapping("/getCards")
    public ResponseEntity<List<CustomerCard>> getCustomerCards(HttpServletRequest request) {
        // TODO fare test
        String email = jwtUtils.getEmailFromRequest(request);
        List<CustomerCard> customerCards = customerCardRepository.findByCustomer_Email(email);
        return new ResponseEntity<>(customerCards, HttpStatus.OK);
    }
}
