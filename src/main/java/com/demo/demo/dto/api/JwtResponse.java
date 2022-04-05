package com.demo.demo.dto.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    @Value("${app.jwt.token.type}")
    private String tokenType;

    private String tokenBody;
    private String tokenRefresh;
    private String username;

    private List<String> roles;

    public JwtResponse(String tokenBody, String tokenRefresh) {
        this.tokenBody = tokenBody;
        this.tokenRefresh = tokenRefresh;
    }
}
