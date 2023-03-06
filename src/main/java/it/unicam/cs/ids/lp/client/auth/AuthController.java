package it.unicam.cs.ids.lp.client.auth;


import it.unicam.cs.ids.lp.JWT_auth.JwtUtils;
import it.unicam.cs.ids.lp.JWT_auth.Role;
import it.unicam.cs.ids.lp.client.Customer;
import it.unicam.cs.ids.lp.client.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("client/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomerDetailsImpl customerDetails = (CustomerDetailsImpl) authentication.getPrincipal();
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(customerDetails);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new CustomerInfoResponse(customerDetails.getId(), customerDetails.getName(),
                        customerDetails.getSurname(), customerDetails.getEmail()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        if (customerRepository.existsByEmail(signUpRequest.email)) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        Customer customer = new Customer();
        customer.setName(signUpRequest.name);
        customer.setSurname(signUpRequest.surname);
        customer.setEmail(signUpRequest.email);
        customer.setPassword(encoder.encode(signUpRequest.password));
        customer.setTelephoneNumber(signUpRequest.telephoneNumber);
        customer.setRegistrationDate(LocalDate.now());

        Set<Role> roles = new HashSet<>();
        if (signUpRequest.roles == null)
            roles.add(Role.ROLE_USER);
        else
            roles = signUpRequest.roles.stream()
                    .map(this::getRole)
                    .collect(Collectors.toSet());
        customer.setRoles(roles);
        customerRepository.save(customer);
        return ResponseEntity.ok("User registered successfully!");
    }

    private Role getRole(String role) {
        return switch (role) {
            case "admin" -> Role.ROLE_ADMIN;
            case "mod" -> Role.ROLE_MODERATOR;
            default -> Role.ROLE_USER;
        };
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("You've been signed out!");
    }

    private record CustomerInfoResponse(long id, String name, String surname, String email) {
    }

    private record LoginRequest(String email, String password) {
    }

    private record SignupRequest(String name, String surname, String email, String password, String telephoneNumber,
                                 Set<String> roles) {
    }
}