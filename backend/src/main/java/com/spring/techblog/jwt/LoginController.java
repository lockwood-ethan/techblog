package com.spring.techblog.jwt;

import com.spring.techblog.exceptions.TokenRefreshException;
import com.spring.techblog.models.RefreshToken;
import com.spring.techblog.models.Users;
import com.spring.techblog.payload.request.LoginRequest;
import com.spring.techblog.payload.response.MessageResponse;
import com.spring.techblog.payload.response.UserInfoResponse;
import com.spring.techblog.repositories.RefreshTokenRepository;
import com.spring.techblog.repositories.UserRepository;
import com.spring.techblog.services.RefreshTokenService;
import com.spring.techblog.services.UserDetailsImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    private UserRepository userRepository;

    @Value("${spring.app.jwtRefreshCookieName}")
    private String jwtRefreshCookie;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (AuthenticationException exception) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtAccessToken = jwtUtils.generateJwtAccessCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        ResponseCookie jwtRefreshCookie = jwtUtils.generateJwtRefreshCookie(refreshToken.getToken());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtAccessToken.toString())
                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.isEnabled(),
                        roles
                ));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@RequestBody HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Optional<RefreshToken> refreshToken = Optional.empty();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(jwtRefreshCookie)) {
                String refreshTokenCookie = cookie.getValue();
                refreshToken = refreshTokenRepository.findByToken(refreshTokenCookie);
            }
        }
        if (refreshToken.isPresent()) {
            Users user = refreshToken.get().getUser();
            refreshTokenService.deleteByUser(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        ResponseCookie jwtAccessCookie = jwtUtils.getCleanJwtCookie();
        ResponseCookie jwtRefreshCookie = jwtUtils.getCleanJwtRefreshCookie();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtAccessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                .body(new MessageResponse("Logged out successfully"));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody HttpServletRequest request) {
        String token = jwtUtils.getJwtRefreshFromCookies(request);

        if ((token != null) && (token.length() > 0)) {
            return  refreshTokenService.findByToken(token)
                    .map(refreshTokenService::verifyExpiration)
                    .map(RefreshToken::getUser)
                    .map(user -> {
                        ResponseCookie jwtAccessCookie = jwtUtils.generateJwtAccessCookie(user);
                        return ResponseEntity.ok()
                                .header(HttpHeaders.SET_COOKIE, jwtAccessCookie.toString())
                                .body(new MessageResponse("Token refreshed successfully"));
                    })
                    .orElseThrow(() -> new TokenRefreshException(token,
                            "Refresh token is not in Database"));
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Refresh token is empty"));
    }
}
