package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.entity.ShopUser;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UserDTO {

    private int id;
    private String nick;

    public static UserDTO fromEntity(ShopUser shopUser) {
        return new UserDTO(shopUser.getId(),
                shopUser.getEmail());
    }
}
