package org.example.entity;

import lombok.*;
import org.example.util.OrderStatus;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderedBook {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book product;

    private OrderStatus status;

    private int quantity;

    private int price;
}
