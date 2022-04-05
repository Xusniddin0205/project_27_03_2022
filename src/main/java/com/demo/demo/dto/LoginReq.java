package com.demo.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginReq {

    @NotBlank(message = "Malumot kiritilishi kerak")
    private String userName;

    @NotBlank(message = "Malumot kiritilishi kerak")
    private String password;
}
