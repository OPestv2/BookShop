package org.example.entity;

import lombok.*;
import org.example.util.OrderStatus;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Orders {
//
//    public Order(Customer customer){
//        this.customer = customer;
//        this.books = new ArrayList<>();
//        this.orderStatus = OrderStatus.AWAITING_FOR_PAYMENT;
//    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(targetEntity = OrderedBook.class)
    @JoinColumn(name = "book_id")
    private List<OrderedBook> books;

    private OrderStatus orderStatus;

    public int getTotalPrice() {
        int sum = 0;
        for(OrderedBook orderedBook : books){
            sum += orderedBook.getBook().getPrice();
        }
        return sum;
    }
}