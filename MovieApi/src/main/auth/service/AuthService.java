package com.movieflix.auth.service;

import com.movieflix.auth.entities.User;
import com.movieflix.auth.entities.UserRole;
import com.movieflix.auth.repositories.UserRepository;
import com.movieflix.auth.utils.AuthResponse;
import com.movieflix.auth.utils.LoginRequest;
import com.movieflix.auth.utils.RegisterRequest;
import com.movieflix.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final JwtServiceImpl jwtService;

    private final RefreshTokenService refreshTokenService;

    private final AuthenticationManager authenticationManager;
    public AuthResponse register(RegisterRequest registerRequest) throws UserNotFoundException {
        var user = User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(UserRole.USER)
                .build();

        User savedUser = userRepository.save(user);

        var accessToken = jwtService.generateToken(savedUser);

        var refreshToke = refreshTokenService.createRefreshToken(savedUser.getUsername());

        return AuthResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToke.getRefreshToken()).
                build();
    }

    public AuthResponse login(LoginRequest loginRequest) throws UserNotFoundException {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        var user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(()-> new UserNotFoundException(String.format("User not found with email : %s ,Please enter valid email!",loginRequest.getEmail())));

        var accessToken = jwtService.generateToken(user);
        var refreshToken = refreshTokenService.createRefreshToken(loginRequest.getEmail());

        return AuthResponse
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }

}
