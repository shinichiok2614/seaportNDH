package com.example.demo.services;

import com.example.demo.dtos.CategoryDTO;
import com.example.demo.dtos.PostDTO;
import com.example.demo.dtos.PostImageDTO;
import com.example.demo.exceptions.DataNotFoundException;
import com.example.demo.exceptions.InvalidParamException;
import com.example.demo.models.Category;
import com.example.demo.models.Post;
import com.example.demo.models.PostImage;
import com.example.demo.models.User;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.PostImageRepository;
import com.example.demo.repositories.PostRepository;
import com.example.demo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService implements IPostService {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final PostImageRepository postImageRepository;

    @Override
    public Post createPost(PostDTO postDTO) throws DataNotFoundException {
        Category category = categoryRepository.findById(postDTO.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find category with id: " + postDTO.getCategoryId()));
        User user = userRepository.findById(postDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find user with id: " + postDTO.getUserId()
                ));
        Post post = Post.builder()
                .name(postDTO.getName())
                .thumnail(postDTO.getThumbnail())
                .status(postDTO.getStatus())
                .category(category)
                .user(user)
                .build();
        return postRepository.save(post);
    }

    @Override
    public Post getPostById(long id) throws DataNotFoundException {
        return postRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find post with id= " + id
                ));
    }

    @Override
    public Page<Post> getAllPost(PageRequest pageRequest) {
        return postRepository.findAll(pageRequest);
    }

    @Override
    public Post updatePost(long id, PostDTO postDTO) throws DataNotFoundException {
        Post post = getPostById(id);
//        if (post == null) {
//            return null
//        }
        Category category = categoryRepository.findById(postDTO.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find category with id: " + postDTO.getCategoryId()));
        User user = userRepository.findById(postDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find user with id: " + postDTO.getUserId()
                ));
        post.setName(postDTO.getName());
        post.setThumnail(postDTO.getThumbnail());
        post.setStatus(postDTO.getStatus());
        post.setCategory(category);
        post.setUser(user);
        return postRepository.save(post);
    }

    @Override
    public void deletePost(long id) throws DataNotFoundException {
//        Post post = getPostById(id);
//        postRepository.deleteById(id);
        Optional<Post> post = postRepository.findById(id);
        post.ifPresent(postRepository::delete);
    }

    @Override
    public boolean existsByName(String name) {
        return postRepository.existsByName(name);
    }

//    private PostImage createPostImage(Long id, PostImage postImage) throws DataNotFoundException {
//        Post post = postRepository.findById(id).orElseThrow(() -> new DataNotFoundException(
//                "Cannot find post with id: " + id
//        ));
//        PostImage postImage1 = PostImage.builder()
//                .postId(post)
//                .imageUrl(postImage.getImageUrl())
//                .build();
//        return postImageRepository.save(postImage1);
//    }
public PostImage createPostImage(Long id, PostImageDTO postImageDTO) throws InvalidParamException, DataNotFoundException {
        Post post = postRepository.findById(id).orElseThrow(() -> new DataNotFoundException(
                "Cannot find post with id: " + id
        ));
        PostImage postImage1 = PostImage.builder()
                .post(post)
                .imageUrl(postImageDTO.getImageUrl())
                .build();
        int size= postImageRepository.findByPostId(post.getId()).size();
        if (size >= 5) {
            throw new InvalidParamException("Number of images must be <= 5");
        }
        return postImageRepository.save(postImage1);
    }
}
