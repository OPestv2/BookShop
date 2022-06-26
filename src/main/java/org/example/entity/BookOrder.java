package org.example.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BookOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private ShopUser shopUser;

    @ManyToOne(targetEntity = OrderedBook.class)
    @JoinColumn(name = "book_id")
    private List<OrderedBook> books;

    private int totalPrice;
}