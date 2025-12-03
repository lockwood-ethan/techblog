package com.spring.techblog.repositories;

import com.spring.techblog.models.Comment;
import com.spring.techblog.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {

    Page<Comment> findAllByPostId(UUID postId, PageRequest pageRequest);

    boolean existsByIdAndOwner(UUID id, String owner);

    Comment findCommentByIdAndOwner(UUID id, String owner);
}
