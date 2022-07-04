package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.entity.Customer;

@Getter
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String nick;

    public static UserDTO fromEntity(Customer customer) {
        return new UserDTO(customer.getId(), customer.getEmail());
    }
}
