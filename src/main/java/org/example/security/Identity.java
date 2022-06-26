package org.example.security;

import org.example.entity.ShopUser;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Identity {

    @Autowired
    private UserService userService;

    public ShopUser getCurrent(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<ShopUser> userOptional = userService.getUserByEmail(authentication.getName());
        return userOptional.orElse(null);
    }
}