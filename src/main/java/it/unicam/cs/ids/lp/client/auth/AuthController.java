package it.unicam.cs.ids.lp.client.auth;


import it.unicam.cs.ids.lp.JWT_auth.JwtUtils;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("client/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private CustomerMapper customerMapper;

    @PostMapping("/signin")
    public ResponseEntity<CustomerInfoResponse> authenticateUser(@RequestBody CustomerLoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomerDetailsImpl customerDetails = (CustomerDetailsImpl) authentication.getPrincipal();
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(customerDetails);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new CustomerInfoResponse(customerDetails.getId(), customerDetails.getName(),
                        customerDetails.getSurname(), customerDetails.getEmail()));
    }

    @PostMapping("/signup")
    public ResponseEntity<CustomerInfoResponse> registerUser(@RequestBody CustomerSignupRequest signUpRequest) {
        if (customerRepository.existsByEmail(signUpRequest.email()))
            return ResponseEntity.badRequest()
                    .body(null);

        Customer customer = customerMapper.apply(signUpRequest);
        customerRepository.save(customer);
        return authenticateUser(new CustomerLoginRequest(signUpRequest.email(), signUpRequest.password()));
    }

    @PostMapping("/signout")
    public ResponseEntity<String> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("You've been signed out!");
    }
}
