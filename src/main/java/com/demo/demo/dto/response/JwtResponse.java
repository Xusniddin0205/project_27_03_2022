package com.demo.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    @Value("${app.jwt.token.type}")
    private String tokenType;
    private String tokenBody;
    private String tokenRefresh;
    private String username;


    public JwtResponse(String tokenBody, String tokenRefresh) {
        this.tokenBody = tokenBody;
        this.tokenRefresh = tokenRefresh;
    }
}
