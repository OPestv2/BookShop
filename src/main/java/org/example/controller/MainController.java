package org.example.controller;

import org.example.entity.ShopUser;
import org.example.security.JwtTokenProvider;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    String main(){
        return "main";
    }

    @GetMapping("/login")
    String getLogin(){
        return "login";
    }

    @PostMapping("/login")
    String postLogin(Model model, @ModelAttribute("user") ShopUser user){
        String a = "esfe";
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getEmail(), user.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // get token form tokenProvider
            String token = jwtTokenProvider.generateToken(authentication);
            model.addAttribute("token", token);
            return "books";
        }catch (AuthenticationException e) {
            return "login";
        }
    }

    @GetMapping("/register")
    String getRegister(){
        return "register";
    }

    @PostMapping("/register")
    String postRegister(Model model, @ModelAttribute("user") ShopUser user){

        if(user.getEmail().length() == 0 || user.getEmail().length() > 100 || userService.getUserByEmail(user.getEmail()).isPresent() && user.getPassword().length()> 5)
            return "register";

        ShopUser newuser = new ShopUser();
        newuser.setEmail(user.getEmail());
        newuser.setPassword(passwordEncoder.encode(user.getPassword()));
        newuser.setRole("USER");

        userService.saveUser(newuser);
        return "redirect:/login";
    }

    @RequestMapping("/logout")
    String logout(){
        return "main";
    }

    @RequestMapping("/books")
    String books(Model model){
        model.addAttribute("some_key","some_value");
        return "books";
    }

    @RequestMapping("/books/{id}")
    String book(String id, Model model){
        model.addAttribute("some_key","some_value");
        return "book_details";
    }
}
