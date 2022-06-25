//package org.example.entity;
//
//import lombok.*;
//
//import javax.persistence.*;
//import java.util.UUID;
//
//@Entity
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
//@Builder
//public class Order {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private UUID id;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @ManyToOne
//    @JoinColumn(name = "book_id")
//    private Book book;
//
//    private int totalPrice;
//}
