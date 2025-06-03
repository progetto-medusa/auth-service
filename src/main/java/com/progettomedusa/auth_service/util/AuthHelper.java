package com.progettomedusa.auth_service.util;


import java.time.Instant;
import java.util.stream.Collectors;

import com.progettomedusa.auth_service.model.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthHelper {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public String createUserToken(LoginResponse loginResponse) {
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(60 * 30))
                .subject(loginResponse.getDomain())
                .claim("role", "user")
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
    }

    public Jwt validateToken(String token) {
        return jwtDecoder.decode(token);
    }

    public String refreshUserToken(String token) {
        Jwt jwt = validateToken(token);
        String username = jwt.getSubject();
        String role = (String) jwt.getClaims().get("role");

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setDomain(username);
        loginResponse.setMessage(role);

        return createUserToken(loginResponse);
    }
}
