package com.demo.demo.security;

import com.demo.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println(username);
       // System.out.println(userRepository.findByUserName(username).get().toString());
        return userRepository.findByUserName(username).
                orElseThrow(()->new UsernameNotFoundException(username+" topilmadi"));
    }

    public UserDetails loadUserById(Integer id){
        return userRepository.findById(id).
                orElseThrow(()->new UsernameNotFoundException(id+" topilmadi"));
            }
}
