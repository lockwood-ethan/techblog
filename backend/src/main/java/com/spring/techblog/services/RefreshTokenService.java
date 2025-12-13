package com.spring.techblog.services;

import com.spring.techblog.exceptions.TokenRefreshException;
import com.spring.techblog.models.RefreshToken;
import com.spring.techblog.models.Users;
import com.spring.techblog.repositories.RefreshTokenRepository;
import com.spring.techblog.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Value("${spring.app.jwtRefreshExpirationMs}")
    private Long jwtRefreshExpirationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(UUID userId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userRepository.findById(userId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(jwtRefreshExpirationMs));
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh Token was expired. Please make a new signin request");
        }
        return token;
    }

    @Transactional
    public void deleteByUser(Users user) {
        refreshTokenRepository.deleteByUser_Id(user.getId());
    }

}
