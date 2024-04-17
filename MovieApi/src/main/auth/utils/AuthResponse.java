package com.movieflix.auth.utils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NonNull
@Builder
public class AuthResponse {

    private String accessToken;

    private String refreshToken;


}
