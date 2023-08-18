package com.membership.restaurant.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(name = "uc_user_account", columnNames = {"account"})
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;

    @NotEmpty
    @Column(name = "account", nullable = false, unique = true)
    private String account;

    @NotEmpty
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "tel")
    private String tel;

    @NotNull
    @Column(name = "role", nullable = false)
    private Role role;

    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "birthday")
    private String birthday;

    public User() {
        this.role = Role.USER;
    }
}