package com.membership.restaurant.services;

import com.membership.restaurant.entities.Role;
import com.membership.restaurant.entities.User;
import com.membership.restaurant.repositories.UserRepository;
import com.membership.restaurant.utils.BCryptHashGenerator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User getUserById(int id) {
        return userRepository.findUserById(id);
    }

    public User getUserByName(String account) {
        return userRepository.findUserByAccount(account);
    }

    public boolean isExisted(int id) {
        return userRepository.existsUserById(id);
    }

    public boolean isExistedByName(String account) {
        return userRepository.existsUserByAccount(account);
    }

    public void deleteUser(int id) {
        if (this.isExisted(id)) {
            userRepository.deleteUserById(id);
        } else {
            log.warn("User is not existed!");
        }
    }


    public void updateUserInfo(int id, String account, String password, Role role) {
        if (!this.isExisted(id)) {
            log.warn("The user " + id + "is not existed.");
            return;
        }
        password = BCryptHashGenerator.generateHash(password);
        User user = this.getUserById(id);
        user.setAccount(account);
        user.setPassword(password);
        user.setRole(role);
        userRepository.save(user);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
