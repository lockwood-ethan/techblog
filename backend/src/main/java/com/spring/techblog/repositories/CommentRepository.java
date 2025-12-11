package com.spring.techblog.repositories;

import com.spring.techblog.models.Comment;
import com.spring.techblog.models.Post;
import com.spring.techblog.models.Users;
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

    boolean existsByIdAndUser(UUID id, Users user);

    Comment findCommentByIdAndUser(UUID id, Users user);
}
