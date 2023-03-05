package it.unicam.cs.ids.lp.client.registration;

import it.unicam.cs.ids.lp.client.Customer;
import it.unicam.cs.ids.lp.client.CustomerRepository;
import it.unicam.cs.ids.lp.client.card.CustomerCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class CustomerRegistrationController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomerRegistrationService customerRegistrationService;
    @Autowired
    private CustomerRepository customerRepository;

    @PutMapping("/customerRegistration/register")
    public ResponseEntity<?> registerCustomer(@RequestBody CustomerRequest customerRequest) {
        Customer customer = setCustomer(customerRequest);
        CustomerCard customerCard = setCustomerCard(customerRequest);
        boolean registered = customerRegistrationService.registerCustomer(customer, customerCard);
        if (registered) return new ResponseEntity<>(HttpStatus.CREATED);
        else return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    private Customer setCustomer(CustomerRequest customerRequest) {
        Customer customer = new Customer();
        customer.setName(customerRequest.name());
        customer.setSurname(customerRequest.surname());
        customer.setTelephoneNumber(customerRequest.telephoneNumber());
        customer.setEmail(customerRequest.email());
        customer.setPassword(passwordEncoder.encode(customerRequest.password()));
        customer.setRegistrationDate(LocalDate.now());
        return customer;
    }

    private CustomerCard setCustomerCard(CustomerRequest customerRequest) {
        CustomerCard customerCard = new CustomerCard();
        customerCard.setFamily(customerRequest.isFamily);
       // TODO customerCard.setCustomer(customerRepository.findById(customerRequest).orElseThrow());
        return customerCard;
    }

    @DeleteMapping("/customerUnregistration/{name}")
    public ResponseEntity<?> unregisterCustomer(@PathVariable String name) {
        customerRegistrationService.unregisterCustomerByName(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    protected record CustomerRequest(String name, String surname, String telephoneNumber, String email,
                                     Boolean isFamily,
                                     String password) {
    }
}
