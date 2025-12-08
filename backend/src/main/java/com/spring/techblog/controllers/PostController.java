package com.spring.techblog.controllers;

import com.spring.techblog.models.Post;
import com.spring.techblog.repositories.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("/{requestedId}")
    private ResponseEntity<Optional<Post>> findById(@PathVariable UUID requestedId) {
        Optional<Post> post = postRepository.findById(requestedId);
        if (post.isPresent()) {
            return ResponseEntity.ok(post);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    private ResponseEntity<List<Post>> findAll(Pageable pageable) {
        Page<Post> page = postRepository.findAll(PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by(Sort.Direction.DESC, "createdDate"))
        ));
        return ResponseEntity.ok(page.getContent());
    }

    @PostMapping
    private ResponseEntity<Void> createPost(@RequestBody Post newPostRequest, UriComponentsBuilder ucb, Principal principal) {
        Post postWithOwner = new Post(null, newPostRequest.getTitle(), newPostRequest.getBody(), principal.getName(), Instant.now(), Instant.now());
        Post savedPost = postRepository.save(postWithOwner);
        URI locationOfNewPost = ucb
                .path("/posts/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();
        return ResponseEntity.created(locationOfNewPost).build();
    }

    @PutMapping("/{requestedId}")
    private ResponseEntity<Void> updatePost(@PathVariable UUID requestedId, @RequestBody Post postUpdateRequest, Principal principal) {
        Post post = postRepository.findByIdAndOwner(requestedId, principal.getName());
        if (post != null) {
            Post updatedPost = new Post(post.getId(), postUpdateRequest.getTitle(), postUpdateRequest.getBody(), principal.getName(), post.getCreatedDate(), Instant.now());
            postRepository.save(updatedPost);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{requestedId}")
    private ResponseEntity<Void> deletePost(@PathVariable UUID requestedId, Principal principal) {
        if (postRepository.existsByIdAndOwner(requestedId, principal.getName())) {
            postRepository.deleteById(requestedId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
