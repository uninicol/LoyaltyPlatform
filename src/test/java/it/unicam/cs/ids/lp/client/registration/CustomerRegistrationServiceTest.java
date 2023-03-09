package it.unicam.cs.ids.lp.client.registration;

import it.unicam.cs.ids.lp.client.Customer;
import it.unicam.cs.ids.lp.client.auth.AuthController;
import it.unicam.cs.ids.lp.client.auth.CustomerSignupRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CustomerRegistrationServiceTest {

    @Autowired
    private AuthController authController;
    //@Autowired
    //private CustomerCardMapper customerCardMapper;

    @Test
    void registerCustomer() {
        assertThrows(NullPointerException.class,
                () -> authController.registerUser(
                        new CustomerSignupRequest(null, null, null, null, null, null)));
        Customer customer = new Customer();
        customer.setName("Steve");
        customer.setSurname("jobs");
        customer.setEmail("StivJobs@gmail.com");
        customer.setTelephoneNumber("132-456-7890");
        customer.setPassword("SteveIlJobs");
        //CustomerCard customerCard = customerCardMapper.apply(customer.getEmail(), )
        //Assertions.assertTrue(authController.registerUser());
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
