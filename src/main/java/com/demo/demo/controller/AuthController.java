package com.demo.demo.controller;

import com.demo.demo.dto.LoginReq;
import com.demo.demo.dto.api.JwtResponse;
import com.demo.demo.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping(value = {"", "/"})
    public ResponseEntity<?> login(@RequestBody @Valid LoginReq loginReq) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginReq.getUserName(), loginReq.getPassword()));
        String token = jwtTokenProvider.generateToken(authentication);
        System.out.println(token);
        if (!authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.CHECKPOINT).body("Foydalanuvchi logini mavjud emas");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(token));
    }

}
