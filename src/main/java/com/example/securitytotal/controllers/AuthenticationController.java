package com.example.securitytotal.controllers;

import com.example.securitytotal.dto.AuthenticationRequest;
import com.example.securitytotal.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @PostMapping()
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.login(), request.password()));
            final UserDetails user = userDetailsService.loadUserByUsername(request.login());
            if (user != null) {
                return new ResponseEntity<>(jwtService.generateToken(user), HttpStatus.OK);
            }
            else return ResponseEntity.status(401).body("Authentication failed");
        } catch (AuthenticationException e){
            return ResponseEntity.status(401).body("Authentication failed");
        }
    }
}
