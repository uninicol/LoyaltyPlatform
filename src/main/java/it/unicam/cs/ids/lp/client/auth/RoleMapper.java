package it.unicam.cs.ids.lp.client.auth;

import it.unicam.cs.ids.lp.JWT_auth.Role;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class RoleMapper implements Function<String, Role> {
    @Override
    public Role apply(String roleString) {
        return switch (roleString) {
            case "admin" -> Role.ROLE_ADMIN;
            case "mod" -> Role.ROLE_MODERATOR;
            default -> Role.ROLE_USER;
        };
    }
}
