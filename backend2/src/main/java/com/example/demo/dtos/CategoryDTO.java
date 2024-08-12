package com.example.demo.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    @NotEmpty(message = "Category's name cannot be empty")
    private String name;
}

//@Setter
//@Getter
//@Data t oString
//@AllArgsConstructor
//@NoArgsConstructor