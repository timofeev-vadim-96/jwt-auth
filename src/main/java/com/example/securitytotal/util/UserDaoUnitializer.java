package com.example.securitytotal.util;

import com.example.securitytotal.dao.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserDaoUnitializer {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @EventListener(ContextRefreshedEvent.class)
    public void initialize(){
        List<UserDetails> userDetails = List.of
                (new User(
                                "admin",
                                passwordEncoder.encode("admin"),
                                Collections.singleton(new SimpleGrantedAuthority("ADMIN"))),
                        new User(
                                "user",
                                passwordEncoder.encode("user"),
                                Collections.singleton(new SimpleGrantedAuthority("USER"))));
        userDao.setUserDetailsList(userDetails);
    }
}
