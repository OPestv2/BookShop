package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class SignUpDto {
    @Schema(example = "john")
    private String email;
    @Schema(example = "john")
    private String password;
}
