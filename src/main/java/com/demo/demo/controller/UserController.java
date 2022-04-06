package com.demo.demo.controller;

import com.demo.demo.entity.User;
import com.demo.demo.security.CurrentUser;
import com.demo.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/me")
    public ResponseEntity<?> user(@CurrentUser User user){
        return ResponseEntity.ok(user);
    }

    @GetMapping("/all")
    public ResponseEntity<?> users(){

        return ResponseEntity.ok(userService.getAll());
    }
}
