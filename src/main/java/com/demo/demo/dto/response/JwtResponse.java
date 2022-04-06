package com.demo.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

    private String tokenType="RestApi";
    private String tokenBody;
    private String tokenRefresh;
    private String username;

    public JwtResponse(String tokenBody, String tokenRefresh, String username) {
        this.tokenBody = tokenBody;
        this.tokenRefresh = tokenRefresh;
        this.username = username;
    }

    public JwtResponse(String tokenBody, String tokenRefresh) {
        this.tokenBody = tokenBody;
        this.tokenRefresh = tokenRefresh;
    }
}
