package com.movieflix.controller;

import com.movieflix.auth.entities.RefreshToken;
import com.movieflix.auth.entities.User;
import com.movieflix.auth.service.AuthService;
import com.movieflix.auth.service.JwtServiceImpl;
import com.movieflix.auth.service.RefreshTokenService;
import com.movieflix.auth.utils.AuthResponse;
import com.movieflix.auth.utils.LoginRequest;
import com.movieflix.auth.utils.RefreshTokenRequest;
import com.movieflix.auth.utils.RegisterRequest;
import com.movieflix.exception.RefreshTokenNotFoundException;
import com.movieflix.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtServiceImpl jwtService;


    @PostMapping("register/")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) throws UserNotFoundException {

        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping("login/")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) throws UserNotFoundException {

        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("refresh/")
    public ResponseEntity<AuthResponse>  refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) throws UserNotFoundException, RefreshTokenNotFoundException {
        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(refreshTokenRequest.getRefreshToken());
        User user = refreshToken.getUser();

        String accessToken = jwtService.generateToken(user);

        return ResponseEntity.ok(
                AuthResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken.getRefreshToken())
                        .build()
        );

    }
}
