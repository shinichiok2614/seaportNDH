package com.example.demo.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    @JsonProperty("firstname")
    private String firstName;
    @JsonProperty("lastname")
    private String lastName;
    private String address;
    private String department;
    @NotBlank(message = "Password cannot be blank")
    private String password;
    @NotBlank(message = "Retype password cannot be blank")
    @JsonProperty("retype_password")
    private String retypePassword;
    @JsonProperty("is_active")
    private int isActive;
    @JsonProperty("date_of_birth")
    private Date dateOfBirth;
    @NotBlank(message = "Phone number is required")
    private String phone;
    @JsonProperty("facebook_account_id")
    private int facebookAccountId;
    @JsonProperty("google_account_id")
    private int googleAccountId;
    @NotNull(message = "Role ID is required")
    @JsonProperty("role_id")
    private Long roleId;

}
