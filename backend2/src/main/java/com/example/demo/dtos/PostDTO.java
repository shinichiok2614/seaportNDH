package com.example.demo.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 200, message = "Title must be between 3 and 20 characters")
    private String name;
    private String thumbnail;
    private String status;
    @JsonProperty("category_id") //mapping
    private Long categoryId;
    @JsonProperty("user_id")
    private Long userId;
//    @Min(value = 0, message = "Price must be greater than or equal to 0")
//    @Max(value = 10000000, message = "Price must be less than or equal to 10, 000, 000")
//    private Float price;

    private MultipartFile file;
    private List<MultipartFile> files;

//    public MultipartFile getFile() {
//        return file;
//    }

}
