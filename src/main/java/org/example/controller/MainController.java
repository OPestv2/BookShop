package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping
    String getMain(Model model){
        model.addAttribute("some_key","some_value");
        return "main";
    }

    @GetMapping("/login")
    String getLogin(Model model){
        model.addAttribute("some_key","some_value");
        return "login";
    }
}
