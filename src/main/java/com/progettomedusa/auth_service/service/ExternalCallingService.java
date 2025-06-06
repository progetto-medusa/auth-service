package com.progettomedusa.auth_service.service;


import com.progettomedusa.auth_service.model.request.AuthRequest;
import okhttp3.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.progettomedusa.auth_service.config.OkHttpClientCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.progettomedusa.auth_service.model.response.LoginResponse;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
public class ExternalCallingService {

    private final ObjectMapper objectMapper;
    private final OkHttpClientCustom okHttpClientCustom;

    public LoginResponse retrieveUserData(String url, AuthRequest authRequest, String appKeyHeader) throws IOException {

        String json = objectMapper.writeValueAsString(authRequest);
        RequestBody requestBody = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .addHeader("X-APP-KEY", appKeyHeader)
                .post(requestBody)
                .build();

        Response response = okHttpClientCustom.okHttpClient().newCall(request).execute();
        String responseBody = response.body().string();

        log.info("Risposta ricevuta dallo user-service: {}", responseBody);

        return objectMapper.readValue(responseBody, LoginResponse.class);
    }
}

