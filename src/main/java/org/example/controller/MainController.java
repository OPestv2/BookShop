package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class MainController {

    @RequestMapping
    String main(Model model){
        model.addAttribute("some_key","some_value");
        return "main";
    }

    @RequestMapping("/login")
    String login(Model model){
        model.addAttribute("some_key","some_value");
        return "login";
    }

    @RequestMapping("/register")
    String register(Model model){
        model.addAttribute("some_key","some_value");
        return "register";
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
