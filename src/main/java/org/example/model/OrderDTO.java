package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.entity.Order;
import org.example.util.OrderStatus;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Setter
@Getter
@AllArgsConstructor
public class OrderDTO {
    private UUID id;
    private UserDTO userDTO;
    private List<OrderedBookDTO> books;
    private OrderStatus orderStatus;
    private int totalPrice;

    public static OrderDTO fromEntity(Order order) {
        return new OrderDTO(order.getId(),UserDTO.fromEntity(order.getUser()), order.getBooks().stream().map(OrderedBookDTO::fromEntity).collect(Collectors.toList()), OrderStatus.AWAITING_FOR_PAYMENT,order.getTotalPrice());
    }
}
