package com.spring.techblog.repositories;

import com.spring.techblog.models.Post;
import com.spring.techblog.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    Post findByIdAndUser(UUID id, Users user);
    boolean existsByIdAndUser(UUID id, Users user);
}
