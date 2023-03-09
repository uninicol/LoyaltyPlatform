package it.unicam.cs.ids.lp.client.card;

import it.unicam.cs.ids.lp.JWT_auth.JwtUtils;
import it.unicam.cs.ids.lp.client.Customer;
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
    private JwtUtils jwtUtils;
    @Autowired
    private CustomerCardResponseMapper customerCardResponseMapper;
    @Autowired
    private CustomerCardMapper customerCardMapper;

    @PutMapping("/addCard/{cardId}")
    public ResponseEntity<String> addCustomerCard(HttpServletRequest request, @PathVariable Long cardId) {
        String email = jwtUtils.getEmailFromRequest(request);
        CustomerCard customerCard = customerCardMapper.apply(email, cardId);
        customerCardRepository.save(customerCard);
        return ResponseEntity.ok()
                .body("Carta aggiunta con successo");
    }

    @GetMapping("/getCards")
    public ResponseEntity<List<CustomerCardResponse>> getCustomerCards(HttpServletRequest request) {
        Customer customer = jwtUtils.getCustomerFromRequest(request);
        List<CustomerCardResponse> customerCards = customer.getCustomerCard()
                .stream()
                .map(customerCardResponseMapper)
                .toList();
        return new ResponseEntity<>(customerCards, HttpStatus.OK);
    }
}
