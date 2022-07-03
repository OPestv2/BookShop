package org.example.controller;

import org.example.dto.LoginDto;
import org.example.dto.SignUpDto;
import org.example.entity.*;
import org.example.model.BookDTO;
import org.example.security.Identity;
import org.example.security.JwtTokenProvider;
import org.example.service.BookService;
import org.example.service.OrdersService;
import org.example.service.UserService;
import org.example.util.DamnClass;
import org.example.util.OrderStatus;
import org.example.util.TotalPrice;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
        // TODO: zamiast suchej roli dodac new Authority (link: https://kampus.umcs.pl/pluginfile.php/734892/mod_resource/content/1/springSecurity2.pdf)
        if(users > 0) {
            newuser.setRole("USER");
            newuser.getAuthorities().add(new Authority(newuser,"USER"));
        }
        else {
            newuser.setRole("ADMIN");
            newuser.getAuthorities().add(new Authority(newuser,"ADMIN"));
        }

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
        book.setPrice(bookDTO.getPrice());
        book.setTitle(bookDTO.getTitle());
        bookService.save(book);

        return "redirect:/books";
    }

    @GetMapping("/books/remove/{id}")
    String deleteBooks(@PathVariable("id") String id, Model model){
        Long uid = Long.parseLong(id);

        Book book = bookService.findBook(uid);
        bookService.delete(book);

        return "redirect:/books";
    }

    @GetMapping("/basket")
    String basket(Model model){
        Customer user = identity.getCurrent();

        Orders orders = ordersService.findByCustomer(user);
        List<Book> books = new ArrayList<>();

        if(orders == null || orders.getBooks().isEmpty()){
            return "redirect:/books";
        }

        for(OrderedBook orderedBook : orders.getBooks()){
            books.add(orderedBook.getBook());
        }

        TotalPrice price = new TotalPrice(orders.getTotalPrice());

        model.addAttribute("books", books);
        model.addAttribute("user", user);
        model.addAttribute("order", orders);
        model.addAttribute("price", price);

        return "basket";
    }

    @GetMapping("/order/add/{id}")
    String addToBasket(@PathVariable("id") String id, Model model){
        Customer user = identity.getCurrent();
        if(user.getRole().equals("ADMIN"))
            return "redirect:/books";

        Long uid = Long.parseLong(id);

        Book book = bookService.findById(uid);
        OrderedBook orderedBook = new OrderedBook();
        orderedBook.setBook(book);

        Orders order = ordersService.findByCustomer(user);

        if(order == null){
            List<OrderedBook> lista = new ArrayList<>();
            lista.add(orderedBook);
            // Add empty order
            order = new Orders();
            order.setBooks(lista);
            order.setCustomer(user);
            order.setOrderStatus("AWAITING_FOR_PAYMENT");
            ordersService.save(order);
        } else {
            List<OrderedBook> orderedBooks = order.getBooks();
            orderedBooks.add(orderedBook);
            order.setBooks(orderedBooks);
            ordersService.update(order);
        }

        return "redirect:/books";
    }

    @GetMapping("/order/delete/{id}")
    String removeFromBasket(@PathVariable("id") String id, Model model){
        Customer user = identity.getCurrent();
        if(user.getRole().equals("ADMIN"))
            return "redirect:/books";

        Long uid = Long.parseLong(id);

        // Retrieve order
        Orders order = ordersService.findByCustomer(user);

        // Prepare list of new books
        List<OrderedBook> newOrderedBooks = new ArrayList<>();

        // Add every book except this with removed id
        for(OrderedBook book : order.getBooks())
            if(book.getBook().getId() != uid)
                newOrderedBooks.add(book);

        // Update books list in order
        order.setBooks(newOrderedBooks);

        ordersService.save(order);

        return "redirect:/basket";
    }

    @GetMapping("/basket/proceed")
    String clearBasket(Model model){
        Customer user = identity.getCurrent();
        if(user.getRole().equals("ADMIN"))
            return "redirect:/books";

        ordersService.updateOrdersStatus(user, "PAID");

        return "redirect:/books";
    }

    @GetMapping("/basket/manage")
    String showOrders(Model model){
        // show all paid orders
        Customer user = identity.getCurrent();
        if(!user.getRole().equals("ADMIN"))
            return "redirect:/books";

        List<Orders> orders = ordersService.getAll();
        List<DamnClass> finalDamnObjects = new ArrayList<>();

        for(Orders order : orders) {
            if (order.getOrderStatus().equals("PAID")) {
                DamnClass damn = new DamnClass(order, order.getCustomer(), new TotalPrice(order.getTotalPrice()));
                finalDamnObjects.add(damn);
            }
        }

        model.addAttribute("damns", finalDamnObjects);

        return "admin_basket";
    }

    @GetMapping("/basket/manage/{id}")
    String acceptOrder(@PathVariable("id") String id, Model model){

        Customer user = identity.getCurrent();
        if(!user.getRole().equals("ADMIN"))
            return "redirect:/books";

        Long uid = Long.parseLong(id);
        Orders order = ordersService.findById(uid);

        ordersService.updateOrdersStatus(order.getCustomer(), "CONFIRMED");

        return "redirect:/admin_basket";
    }
}
