package com.progettomedusa.auth_service.service;

import com.progettomedusa.auth_service.config.UserServiceProperties;
import com.progettomedusa.auth_service.model.request.AuthRequest;
import com.progettomedusa.auth_service.model.response.AuthResponse;
import com.progettomedusa.auth_service.util.AuthHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.progettomedusa.auth_service.model.response.LoginResponse;


import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthHelper authHelper;
    private final TokenRedisService tokenRedisService;
    private final UserServiceProperties userServiceProperties;
    private final ExternalCallingService externalCallingService;

    public AuthResponse retrieveUserToken(AuthRequest authRequest, String appHeader) throws IOException {
        String url = String.join("",userServiceProperties.getUrl(),"/progetto-medusa/user/login");
        LoginResponse loginResponse = externalCallingService.retrieveUserData(
                url,
               authRequest,
                appHeader
        );
        if (loginResponse.getError() != null) {
            throw new RuntimeException("Errore dallo user-service: " + loginResponse.getError().getMessage());
        }

        String token = authHelper.createUserToken(loginResponse);
        tokenRedisService.storeAuthToken(authRequest.getApplicationId(), token);
        return new AuthResponse(token);
    }
}

