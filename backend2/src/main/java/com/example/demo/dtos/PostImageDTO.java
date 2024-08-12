package com.example.demo.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostImageDTO {
    private int id;

    @NotBlank(message = "PostId not blank")
    @Min(value=1, message = "Post's Id must be > 0")
    @JsonProperty("post_id")
    private int postId;

    @NotBlank(message = "ImageUrl not blank")
    @Size(min = 5, max = 200, message = "Image's name")
    @JsonProperty("image_url")
    private String imageUrl;
}
