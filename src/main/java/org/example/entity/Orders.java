package org.example.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(
        cascade = CascadeType.ALL)
    private List<OrderedBook> books = new ArrayList<>();

    private String orderStatus;

    public int getTotalPrice() {
        int sum = 0;
        for(OrderedBook orderedBook : books){
            sum += orderedBook.getBook().getPrice();
        }
        return sum;
    }
}