package org.example.entity;


import lombok.*;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShopUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String role;
}