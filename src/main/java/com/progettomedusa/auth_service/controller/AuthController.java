package com.progettomedusa.auth_service.controller;


import com.progettomedusa.auth_service.model.request.AuthRequest;
import com.progettomedusa.auth_service.model.response.AuthResponse;
import com.progettomedusa.auth_service.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth")
    public ResponseEntity<Object> auth(HttpServletResponse response, @RequestHeader("X-APP-KEY") String appHeader, @RequestBody AuthRequest authRequest) throws IOException {
        AuthResponse jwtResponse = authService.retrieveUserToken(authRequest, appHeader);
        response.setHeader("Authorization ","Bearer " + jwtResponse.getToken());
        return new ResponseEntity<>(jwtResponse,HttpStatus.OK);
    }

    @PostMapping("/validate")
    public ResponseEntity<Object> validateToken(@RequestBody AuthRequest authRequest) throws IOException {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Object> refreshToken(@RequestBody AuthRequest authRequest) throws IOException {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
