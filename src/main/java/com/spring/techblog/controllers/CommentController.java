package com.spring.techblog.controllers;

import com.spring.techblog.models.Comment;
import com.spring.techblog.models.Post;
import com.spring.techblog.repositories.CommentRepository;
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
@RequestMapping("/comments")
public class CommentController {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentController(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("/comment/{requestedId}")
    private ResponseEntity<Optional<Comment>> findById(@PathVariable UUID requestedId) {
        Optional<Comment> comment = commentRepository.findById(requestedId);
        if (comment.isPresent()) {
            return ResponseEntity.ok(comment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{postId}")
    private ResponseEntity<List<Comment>> findAllCommentsByPostId(@PathVariable UUID postId, Pageable pageable) {
        boolean postExists = postRepository.existsById(postId);
        if (postExists) {
            Post post = postRepository.findById(postId).get();
            Page<Comment> page = commentRepository.findAllByPostId(post.getId(),
                    PageRequest.of(
                            pageable.getPageNumber(),
                            pageable.getPageSize(),
                            pageable.getSortOr(Sort.by(Sort.Direction.DESC, "createdDate"))
                    ));
            return ResponseEntity.ok(page.getContent());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/comment")
    private ResponseEntity<Void> createComment(@RequestBody Comment newCommentRequest, UriComponentsBuilder ucb, Principal principal) {
        Comment commentWithOwner = new Comment(null, newCommentRequest.getPost(), newCommentRequest.getCommentBody(), principal.getName(), Instant.now(), Instant.now());
        Comment savedComment = commentRepository.save(commentWithOwner);
        URI locationOfNewComment = ucb
                .path("/comments/comment/{id}")
                .buildAndExpand(savedComment.getId())
                .toUri();
        return ResponseEntity.created(locationOfNewComment).build();
    }

    @PutMapping("/comment/{requestedId}")
    private ResponseEntity<Void> updateComment(@PathVariable UUID requestedId, @RequestBody Comment commentUpdateRequest, Principal principal) {
        Comment comment = commentRepository.findCommentByIdAndOwner(requestedId, principal.getName());
        if (comment != null) {
            Comment updatedComment = new Comment(comment.getId(), comment.getPost(), commentUpdateRequest.getCommentBody(), principal.getName(), comment.getCreatedDate(), Instant.now());
            commentRepository.save(updatedComment);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/comment/{requestedId}")
    private ResponseEntity<Void> deleteComment(@PathVariable UUID requestedId, Principal principal) {
        if (commentRepository.existsByIdAndOwner(requestedId, principal.getName())) {
            commentRepository.deleteById(requestedId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
