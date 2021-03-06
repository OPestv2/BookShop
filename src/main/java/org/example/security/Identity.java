package org.example.security;

import org.example.entity.Customer;
import org.example.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class Identity {

    private final UserService userService;
    public Identity(UserService userService){
        this.userService = userService;
    }

    public Customer getCurrent(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<Customer> userOptional = userService.findByEmail(authentication.getName());
        return userOptional.orElse(null);
    }
}