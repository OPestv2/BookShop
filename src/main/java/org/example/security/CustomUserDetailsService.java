package org.example.security;

import org.example.entity.ShopUser;
import org.example.service.UserService;
import org.example.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Primary
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserServiceImpl userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ShopUser user = userService.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with email:" + email));
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), getAuthorities());
    }

    private Collection< ? extends GrantedAuthority> getAuthorities(){
        var auth = new ArrayList<SimpleGrantedAuthority>();
        auth.add(new SimpleGrantedAuthority("USER"));
        return auth;
    }
}