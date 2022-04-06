package com.demo.demo.controller;

import com.demo.demo.entity.User;
import com.demo.demo.repository.UserRepository;
import com.demo.demo.security.CurrentUser;
import com.demo.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    @Autowired
    private UserRepository userRepository;


    @GetMapping("/me")
    public ResponseEntity<?> user(@CurrentUser User user){
        return ResponseEntity.ok(user);
    }

    @GetMapping("/all")
    public ResponseEntity<?> users(){

        List<User> users=userService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }


    @GetMapping("/all1")
    public ResponseEntity<?> users1(){


        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll());
    }


}
