package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.entity.Orders;
import org.example.util.OrderStatus;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Setter
@Getter
@AllArgsConstructor
public class OrderDTO {
    private int id;
    private UserDTO userDTO;
    private List<OrderedBookDTO> books;
    private String orderStatus;
    private int totalPrice;

    public static OrderDTO fromEntity(Orders orders) {
        return new OrderDTO(orders.getId(),
                UserDTO.fromEntity(orders.getCustomer()),
                orders.getBooks().stream().map(OrderedBookDTO::fromEntity).collect(Collectors.toList()),
                "AWAITING_FOR_PAYMENT",
                orders.getTotalPrice());
    }
}
