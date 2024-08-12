package com.example.demo.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder //builder pattern   //order
public class UserLoginDTO {
    @NotBlank(message = "Phone number is required")
    private String phone;
    @NotBlank(message = "Password cannot be blank")
    private String password;
}
