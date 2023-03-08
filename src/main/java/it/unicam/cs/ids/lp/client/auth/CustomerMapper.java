package it.unicam.cs.ids.lp.client.auth;

import it.unicam.cs.ids.lp.client.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.function.Function;

@Service
public class CustomerMapper implements Function<CustomerSignupRequest, Customer> {
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public Customer apply(CustomerSignupRequest customerSignupRequest) {
        Customer customer = new Customer();
        customer.setName(customerSignupRequest.name());
        customer.setSurname(customerSignupRequest.surname());
        customer.setEmail(customerSignupRequest.email());
        customer.setPassword(encoder.encode(customerSignupRequest.password()));
        customer.setTelephoneNumber(customerSignupRequest.telephoneNumber());
        customer.setRegistrationDate(LocalDate.now());
        return customer;
    }
}
