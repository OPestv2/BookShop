package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping
    String main(Model model){
        model.addAttribute("some_key","some_value");
        return "main";
    }

    @GetMapping("/login")
    String login(Model model){
        model.addAttribute("some_key","some_value");
        return "login";
    }

    @GetMapping("/register")
    String register(Model model){
        model.addAttribute("some_key","some_value");
        return "register";
    }
}
