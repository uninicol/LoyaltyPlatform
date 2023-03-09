package it.unicam.cs.ids.lp.client.auth;

import java.util.Set;

public record CustomerSignupRequest(
        String name,
        String surname,
        String email,
        String password,
        String telephoneNumber,
        Set<String> roles) {
}
