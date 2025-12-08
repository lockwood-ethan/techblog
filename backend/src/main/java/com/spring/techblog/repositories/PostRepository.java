package com.spring.techblog.repositories;

import com.spring.techblog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    Post findByIdAndOwner(UUID id, String owner);

    boolean existsByIdAndOwner(UUID id, String owner);
}
