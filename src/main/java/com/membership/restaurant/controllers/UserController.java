package com.membership.restaurant.controllers;

import com.membership.restaurant.dtos.requests.UpdatePasswordRequest;
import com.membership.restaurant.dtos.requests.UpdateUserRequest;
import com.membership.restaurant.dtos.responses.UserResponse;
import com.membership.restaurant.entities.User;
import com.membership.restaurant.services.AuthService;
import com.membership.restaurant.services.FuckService;
import com.membership.restaurant.services.UserService;
import com.membership.restaurant.utils.BCryptHashGenerator;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.hibernate.validator.constraints.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Log4j2
public class UserController {
    private final FuckService fuckService;
    private final AuthService authService;

    private final UserService userService;

    @Autowired
    public UserController(AuthService authService, UserService userService, FuckService fuckService) {
        this.authService = authService;
        this.userService = userService;
        this.fuckService = fuckService;
    }

    @GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getUser(@PathVariable("id") @UUID String session_id, HttpServletResponse response) {
        HashMap<String, Object> responseObj = new HashMap();
        HashMap<String, Object> data = new HashMap();
        if (!authService.validate(session_id)) {
            responseObj.put("code", HttpServletResponse.SC_UNAUTHORIZED);
            data.put("message", "401 UNAUTHORIZED - You are not logged-in.");
            responseObj.put("data", data);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return responseObj;
        }
        User user = authService.getUser(session_id);

        response.setStatus(HttpServletResponse.SC_OK);
        responseObj.put("code", HttpServletResponse.SC_OK);

        data.put("user", new UserResponse(user));
        responseObj.put("data", data);

        return responseObj;
    }

    @PostMapping(value = "/updateUserInfo/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> updateUser(@PathVariable("id") @UUID String session_id, HttpServletResponse response, @RequestBody UpdateUserRequest updateUserRequest) {
        Map responseObj = new HashMap();
        Map<String, Object> data = new HashMap();
        if (!authService.validate(session_id)) {
            responseObj.put("code", HttpServletResponse.SC_UNAUTHORIZED);
            data.put("message", "401 UNAUTHORIZED - You are not logged-in.");
            responseObj.put("data", data);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return responseObj;
        }

        User user = authService.getUser(session_id);
        user.setEmail(updateUserRequest.getEmail());
        user.setBirthday(updateUserRequest.getBirthday());
        user.setTel(updateUserRequest.getTel());
        userService.saveUser(user);

        data.put("message", "Success.");
        responseObj.put("data", data);

        response.setStatus(HttpServletResponse.SC_OK);
        responseObj.put("code", HttpServletResponse.SC_OK);

        return responseObj;
    }

    @PostMapping(value = "/updatePassword/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> updatePassword(@PathVariable("id") @UUID String session_id, HttpServletResponse response, @RequestBody UpdatePasswordRequest updatePasswordRequest) {
        Map responseObj = new HashMap();
        Map<String, Object> data = new HashMap();
        if (!authService.validate(session_id)) {
            responseObj.put("code", HttpServletResponse.SC_UNAUTHORIZED);
            data.put("message", "401 UNAUTHORIZED - You are not logged-in.");
            responseObj.put("data", data);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return responseObj;
        }

        User user = authService.getUser(session_id);

        //验证旧密码和数据库中的密码是否匹配
        if (!BCryptHashGenerator.checkPassword(updatePasswordRequest.getOldPassword(), user.getPassword())) {
            responseObj.put("code", HttpServletResponse.SC_FORBIDDEN);
            data.put("message", "403 FORBIDDEN - The oldPassword provided by the client did not match the password which is stored in the database.");
            responseObj.put("data", data);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return responseObj;
        }

        user.setPassword(BCryptHashGenerator.generateHash(updatePasswordRequest.getNewPassword()));
        userService.saveUser(user);

        data.put("message", "Success.");
        responseObj.put("data", data);

        response.setStatus(HttpServletResponse.SC_OK);
        responseObj.put("code", HttpServletResponse.SC_OK);

        return responseObj;
    }

    @GetMapping(value = "/order/{sessionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map getOrders(HttpServletResponse response, @PathVariable String sessionId) {
        Map responseObj = new HashMap();
        Map<String, Object> data = new HashMap();
        if (!authService.validate(sessionId)) {
            responseObj.put("code", HttpServletResponse.SC_UNAUTHORIZED);
            data.put("message", "401 UNAUTHORIZED - You are not logged-in.");
            responseObj.put("data", data);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return responseObj;
        }
        User user = authService.getUser(sessionId);
        List orders = fuckService.findAllByUser(user);
        data.put("orders", orders);
        responseObj.put("data", data);

        response.setStatus(HttpServletResponse.SC_OK);
        responseObj.put("code", HttpServletResponse.SC_OK);

        return responseObj;
    }
}
