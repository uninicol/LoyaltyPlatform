package it.unicam.cs.ids.lp.fsdfdsafdsafdsaf;


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
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Customer customerDetails = (Customer) authentication.getPrincipal();
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(customerDetails);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new CustomerInfoResponse(customerDetails.getId(), customerDetails.getEmail()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        if (customerRepository.existsByEmail(signUpRequest.email)) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        // Create new customer's account
        Customer customer = new Customer();
        customer.setEmail(signUpRequest.email);
        customer.setPassword(encoder.encode(signUpRequest.password));

        customerRepository.save(customer);

        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("You've been signed out!");
    }

    private record CustomerInfoResponse(long id, String email) {
    }

    private record LoginRequest(String email, String password) {
    }

    private record SignupRequest(String email, String password) {
    }
}