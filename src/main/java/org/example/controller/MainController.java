package org.example.controller;

import org.example.dto.LoginDto;
import org.example.dto.SignUpDto;
import org.example.entity.Book;
import org.example.entity.ShopUser;
import org.example.model.BookDTO;
import org.example.security.Identity;
import org.example.security.JwtTokenProvider;
import org.example.service.BookService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Identity identity;

    @GetMapping
    String main(){
        return "main";
    }

    @GetMapping("/login")
    String getLogin(){
        return "login";
    }

    @PostMapping("/login")
    String postLogin(Model model, @ModelAttribute("user") LoginDto user){
        System.out.println(user.getEmail());
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getEmail(), user.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // get token form tokenProvider
            String token = jwtTokenProvider.generateToken(authentication);
            model.addAttribute("token", token);
            return "redirect:/books";
        }catch (AuthenticationException e) {
            return "login";
        }
    }

    @GetMapping("/register")
    String getRegister(){
        return "register";
    }

    @PostMapping("/register")
    String postRegister(Model model, @ModelAttribute("user") SignUpDto user){

        if(user.getEmail().length() == 0 || user.getEmail().length() > 100 || userService.findByEmail(user.getEmail()).isPresent() && user.getPassword().length()> 5)
            return "register";

        ShopUser newuser = new ShopUser();
        newuser.setEmail(user.getEmail());
        newuser.setPassword(passwordEncoder.encode(user.getPassword()));

        int users = userService.getUsers().size();

        if(users > 0)
            newuser.setRole("USER");
        else
            newuser.setRole("ADMIN");

        userService.saveUser(newuser);
        return "redirect:/login";
    }

//    @GetMapping("/logout")
//    String logout(){
//        return "main";
//    }

    @GetMapping("/books")
    String books(Model model){
        ShopUser user = identity.getCurrent();

        List<Book> books = bookService.getAll();
        int count = books.size();

        model.addAttribute("books", books);
        model.addAttribute("count", count);
        model.addAttribute("user", user);
        return "books";
    }

    @PostMapping("/books/add")
    String putBooks(Model model, @ModelAttribute("book") BookDTO bookDTO){

        Book book = new Book();
        book.setPrice(bookDTO.getPrice());
        book.setTitle(bookDTO.getTitle());
        bookService.save(book);

        return "redirect:/books";
    }
}
