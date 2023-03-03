package it.unicam.cs.ids.lp.client.registration;

import it.unicam.cs.ids.lp.client.Customer;
import it.unicam.cs.ids.lp.client.card.CustomerCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CustomerRegistrationServiceTest {

    @Autowired
    private CustomerRegistrationService customerRegistrationService;

    @Test
    void registerCustomer() {
        assertThrows(NullPointerException.class,
                () -> customerRegistrationService.registerCustomer(null, new CustomerCard()));
        Customer customer = new Customer();
        customer.setName("Steve");
        customer.setSurname("jobs");
        customer.setEmail("StivJobs@gmail.com");
        customer.setTelephoneNumber("132-456-7890");
        CustomerCard customerCard = new CustomerCard();
        customerCard.setCustomer(customer);
        customer.setPassword("SteveIlJobs");
        Assertions.assertTrue(customerRegistrationService.registerCustomer(customer, customerCard));
    }

    @Test
    void isNameValid() {

    }

    @Test
    void isAddressValid() {
    }

    @Test
    void isTelephoneNumberValid() {
    }

    @Test
    void isEmailValid() {
    }
}