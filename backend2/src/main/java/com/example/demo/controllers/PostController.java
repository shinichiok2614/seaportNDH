package com.example.demo.controllers;

import com.example.demo.dtos.PostDTO;
import com.example.demo.dtos.PostImageDTO;
import com.example.demo.models.Post;
import com.example.demo.models.PostImage;
import com.example.demo.services.IPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/posts")
@RequiredArgsConstructor //tạo constructor cho nhung thuoc tinh Not Null, bat buoc phai co
public class PostController {
    private final IPostService postService;
    @GetMapping("")
    public ResponseEntity<String> getPosts(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        return ResponseEntity.ok(String.format("getPosts page = %d, limit = %d", page, limit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getPostById(@PathVariable Long id) {
//        return ResponseEntity.ok("getPostById id = "+ id);     //chuc nang giong nhau
        return ResponseEntity.status(HttpStatus.OK).body("getPostById id = " + id);
    }

    //    @Valid
//    phải có @Valid thì mới bắt được result
//    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PostMapping(value = "") //Body-raw-JSON
    public ResponseEntity<?> insertPost(
            @Valid @RequestBody PostDTO postDTO,
//            @Valid @ModelAttribute PostDTO postDTO,   //@ModelAttribute dùng cho Body: form-data
//            @RequestPart("file") MultipartFile file,
            BindingResult result
    ) {

        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            Post post = postService.createPost(postDTO);
            return ResponseEntity.ok("Post created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("e.getMessage()");
        }
    }

    @PostMapping(value = "uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(
            @PathVariable("id") Long postId,
            @ModelAttribute("files") List<MultipartFile> files
    ) {
        try {
            Post post = postService.getPostById(postId);
            files = files == null ? new ArrayList<MultipartFile>() : files;
            // Kiem tra so luong
            if (files.size() > PostImage.MAX_IMAGE) {
                return ResponseEntity.badRequest().body("You can only upload maximum 5 images");
            }
            List<PostImage> postImages = new ArrayList<>();
            for (MultipartFile file : files) {
                if (file.getSize() == 0) {
                    continue;
                }
                // Kiểm tra kích thước file
                if (file.getSize() > 10 * 1024 * 1024) { // Kích thước > 10MB
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("File is too large! Maximum size is 10MB");
                }
                // Kiểm tra định dạng
                String contentType = file.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("File must be an image");
                }
                // Luu vao db
                String filename = storeFile(file);
                PostImage postImage = postService.createPostImage(
                        post.getId(),
                        PostImageDTO.builder()
                                .imageUrl(filename)
                                .build()
                );
                postImages.add(postImage);
            }
            return ResponseEntity.ok().body(postImages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
//            return ResponseEntity.badRequest().body("error");
        }
    }

    private String storeFile(MultipartFile file) throws IOException {
        // Kiem tra ten file
        if (file.getOriginalFilename() == null) {
            throw new IOException("Invalid image format");
        }
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())); //xóa ten cũ
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;    //ten mới là duy nhất
        Path uploadDir = Paths.get("uploads");                          //luu vao thư muc upload
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);                              //tao thu muc upload neu chua co
        }
        Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok("Delete post successfully id =" + id);
    }
}
