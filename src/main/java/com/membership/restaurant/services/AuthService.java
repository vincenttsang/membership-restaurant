package com.membership.restaurant.services;

import com.membership.restaurant.dtos.LoginSession;
import com.membership.restaurant.entities.Role;
import com.membership.restaurant.entities.User;
import com.membership.restaurant.utils.BCryptHashGenerator;
import com.membership.restaurant.utils.KeySessionManager;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class AuthService {
    UserService userService;

    @Autowired
    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public boolean validate(String sessionId) {
        return (KeySessionManager.get(sessionId) != null);
    }

    public Role getRole(String sessionId) {
        if ((KeySessionManager.get(sessionId) != null)) {
            User user = userService.getUserById(KeySessionManager.get(sessionId).getUser_id());
            return user.getRole();
        } else {
            return null;
        }
    }

    public User getUser(String sessionId) {
        if ((KeySessionManager.get(sessionId) != null)) {
            User user = userService.getUserById(KeySessionManager.get(sessionId).getUser_id());
            return user;
        } else {
            return null;
        }
    }

    public String login(String username, String password) {
        if (userService.isExistedByName(username)) {
            User user = userService.getUserByName(username);
            LoginSession loginSession = new LoginSession(user.getId());
            if (BCryptHashGenerator.checkPassword(password, user.getPassword())) {
                log.info("LoginRequest success: returned sessionID.");
                KeySessionManager.put(loginSession.getSession_id(), loginSession);
                return loginSession.getSession_id();
            } else {
                log.error("LoginRequest failed: password mismatched.");
                return null;
            }
        } else {
            log.error("LoginRequest failed: user is not existed.");
            return null;
        }
    }

    public void logout(String sessionId) {
        KeySessionManager.remove(sessionId);
    }

    public boolean register(String account, String password) {
        if (userService.isExistedByName(account)) {
            return false;
        }
        User user = new User();
        user.setAccount(account);
        password = BCryptHashGenerator.generateHash(password);
        user.setPassword(password);
        userService.saveUser(user);
        return true;
    }
}
