package com.demo.demo.controller;

import com.demo.demo.dto.LoginReq;
import com.demo.demo.dto.response.JwtResponse;
import com.demo.demo.dto.response.RefreshTokenRequest;
import com.demo.demo.entity.RefreshToken;
import com.demo.demo.entity.User;
import com.demo.demo.exception.RefreshTokenException;
import com.demo.demo.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginReq loginReq) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginReq.getUserName(), loginReq.getPassword()));
        String token = jwtTokenProvider.generateToken(authentication);
        System.out.println(token);
        if (!authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.CHECKPOINT).body("Foydalanuvchi logini mavjud emas");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user=(User)authentication.getPrincipal();
        RefreshToken refreshToken=jwtTokenProvider.createRefreshToken(user.getId());
        JwtResponse jwtResponse=new JwtResponse();
        jwtResponse.setTokenRefresh(refreshToken.getToken());
        jwtResponse.setTokenBody(jwtTokenProvider.generateToken(authentication));
        return ResponseEntity.status(HttpStatus.OK).body(jwtResponse);
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody RefreshTokenRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        return jwtTokenProvider.findByToken(requestRefreshToken)
                .map(jwtTokenProvider::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtTokenProvider.generateTokenFromUsername(user);
                    return ResponseEntity.ok(new JwtResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new RefreshTokenException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }



}
