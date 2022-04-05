package com.demo.demo.dto.api;

import com.demo.demo.entity.enums.ResponseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private boolean success;
    private ResponseType rs;
    private String message;
}
