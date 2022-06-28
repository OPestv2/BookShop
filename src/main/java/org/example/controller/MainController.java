package org.example.controller;

import org.example.dto.LoginDto;
import org.example.dto.SignUpDto;
import org.example.entity.Book;
import org.example.entity.Customer;
import org.example.entity.OrderedBook;
import org.example.entity.Orders;
import org.example.model.BookDTO;
import org.example.security.Identity;
import org.example.security.JwtTokenProvider;
import org.example.service.BookService;
import org.example.service.OrdersService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.UUID;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private OrdersService ordersService;

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
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getEmail(), user.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
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

        Customer newuser = new Customer();
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

    @GetMapping("/books")
    String books(Model model){
        Customer user = identity.getCurrent();

        List<Book> books = bookService.getAll();

        model.addAttribute("books", books);
        model.addAttribute("user", user);
        return "books";
    }

    @PostMapping("/books/add")
    String putBooks(Model model, @ModelAttribute("book") BookDTO bookDTO){

        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setPrice(bookDTO.getPrice());
        book.setTitle(bookDTO.getTitle());
        bookService.save(book);

        return "redirect:/books";
    }

    @GetMapping("/books/remove/{id}")
    String deleteBooks(@PathVariable("id") String id, Model model){
        int uid = Integer.parseInt(id);

        Book book = bookService.findBook(uid);
        bookService.delete(book);

        return "redirect:/books";
    }

    @GetMapping("/bookOrder/{id}")
    String addToBasket(String id, Model model){
        int uid = Integer.parseInt(id);

        Customer user = identity.getCurrent();

        Book book = bookService.findBook(uid);
        OrderedBook orderedBook = new OrderedBook();
        orderedBook.setBook(book);

        Orders order = ordersService.findByUserId(user.getId());

        List<OrderedBook> orderedBooks = order.getBooks();
        orderedBooks.add(orderedBook);
        order.setBooks(orderedBooks);

        ordersService.update(order);
//        // get user's basket
//        Orders orders = getOrder(id);
//
//        // prepare book to add
//
//
//        // add book to basket
//        List<OrderedBook> books = orders.getBooks();
//
//
//        // update books list in existing basket object
//        orders.setBooks(books);
//
//        // update in repo
//        ordersRepository.updateOrders(orders);

        return "redirect:/books";
    }
}
