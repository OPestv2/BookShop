package org.example.controller;

import org.example.entity.ShopUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class MainController {

    @GetMapping
    String main(){
        return "main";
    }

    @GetMapping("/login")
    String getLogin(){
        return "login";
    }

    @PostMapping("/login")
    String postLogin(Model model, @ModelAttribute("user") ShopUser user, BindingResult result){
        return "books";
    }

    @GetMapping("/register")
    String getRegister(){
        return "register";
    }

    @PostMapping("/register")
    String postRegister(Model model, @ModelAttribute("user") ShopUser user){
        return "login";
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
