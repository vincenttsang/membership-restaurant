package com.membership.restaurant.controllers;

import com.membership.restaurant.dtos.requests.LoginRequest;
import com.membership.restaurant.dtos.requests.RegisterRequest;
import com.membership.restaurant.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Log4j2
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map tryLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session, @RequestBody @Valid LoginRequest loginRequest) {
        Map responseObj = new HashMap();
        String sessionId = authService.login(loginRequest.getAccount(), loginRequest.getPassword());
        if (sessionId == null) {
            responseObj.put("code", HttpServletResponse.SC_UNAUTHORIZED);
            Map data = new HashMap();
            data.put("message", "Wrong username or password.");
            responseObj.put("data", data);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return responseObj;
        }
        responseObj.put("code", HttpServletResponse.SC_OK);
        Map data = new HashMap();
        data.put("message", "Success.");
        data.put("session_id", sessionId);
        data.put("type", authService.getRole(sessionId));
        responseObj.put("data", data);
        response.setStatus(HttpServletResponse.SC_OK);
        return responseObj;
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map tryRegister(HttpServletRequest request, HttpServletResponse response, HttpSession session, @RequestBody @Valid RegisterRequest registerRequest) {
        Map responseObj = new HashMap();
        if (authService.register(registerRequest.getAccount(), registerRequest.getPassword())) {
            Map data = new HashMap();
            data.put("message", "Registered new user.");
            responseObj.put("data", data);
            responseObj.put("code", HttpServletResponse.SC_OK);
            response.setStatus(HttpServletResponse.SC_OK);
            return responseObj;
        }
        Map data = new HashMap();
        data.put("message", "User is already existed!");
        responseObj.put("data", data);
        responseObj.put("code", HttpServletResponse.SC_FORBIDDEN);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        return responseObj;
    }
}
