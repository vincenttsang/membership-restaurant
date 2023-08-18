package com.membership.restaurant.repositories;

import com.membership.restaurant.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

@RepositoryRestResource(exported = false)
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsUserById(int id);

    boolean existsUserByAccount(String account);

    @Modifying
    @Transactional
    void deleteUserById(int id);

    User findUserByAccount(String account);

    User findUserById(int id);
}