package it.unicam.cs.ids.lp.client.auth;

import it.unicam.cs.ids.lp.JWT_auth.Role;
import it.unicam.cs.ids.lp.client.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CustomerMapper implements Function<CustomerSignupRequest, Customer> {
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Customer apply(CustomerSignupRequest customerSignupRequest) {
        Customer customer = new Customer();
        customer.setName(customerSignupRequest.name());
        customer.setSurname(customerSignupRequest.surname());
        customer.setEmail(customerSignupRequest.email());
        customer.setPassword(encoder.encode(customerSignupRequest.password()));
        customer.setTelephoneNumber(customerSignupRequest.telephoneNumber());
        customer.setRegistrationDate(LocalDate.now());
        Set<Role> roles = getRoles(customerSignupRequest);
        customer.setRoles(roles);
        return customer;
    }

    private Set<Role> getRoles(CustomerSignupRequest signUpRequest) {
        Set<Role> roles = new HashSet<>();
        if (signUpRequest.roles() == null || signUpRequest.roles().isEmpty())
            roles.add(roleMapper.apply("user"));
        else
            roles = signUpRequest.roles()
                    .stream()
                    .map(roleMapper)
                    .collect(Collectors.toSet());
        return roles;
    }
}
