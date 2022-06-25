package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.entity.User;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UserDTO {

    private UUID id;
    private String nick;

    public static UserDTO fromEntity(User user) {
        return new UserDTO(user.getId(),
                user.getEmail());
    }
}
