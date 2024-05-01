package com.example.securitytotal.dao;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Getter
@Setter
public class UserDao {
    private List<UserDetails> userDetailsList;

    public UserDetails findUserByLogin(String login) {
        return userDetailsList.stream()
                .filter(user -> user.getUsername().equals(login))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("User with login: " + login + " not found"));
    }
}
