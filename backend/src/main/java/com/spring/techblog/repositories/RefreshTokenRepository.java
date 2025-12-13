package com.spring.techblog.repositories;

import aj.org.objectweb.asm.commons.Remapper;
import com.spring.techblog.models.RefreshToken;
import com.spring.techblog.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository  extends JpaRepository<RefreshToken, UUID> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByUser_Id(UUID userId);
}
