package com.movieflix.controller;

import com.movieflix.auth.service.ForgotPasswordService;
import com.movieflix.auth.utils.ChangePassword;
import com.movieflix.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/forgot-password/")
@RequiredArgsConstructor
public class ForgotPasswordController {

    private final ForgotPasswordService forgotPasswordService;


    @PostMapping("verifyMail/{email}")
    public ResponseEntity<String> verifyEmail(@PathVariable String email) throws UserNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(forgotPasswordService.verifyEmail(email));
    }

    @PostMapping("verifyOtp/{otp}/{email}")
    public ResponseEntity<String> verifyOtp(@PathVariable Integer otp,@PathVariable String email) throws UserNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(forgotPasswordService.verifyOtp(otp,email));
    }

    @PostMapping("changPassword/{email}")
    private ResponseEntity<?> changePassword(@RequestBody ChangePassword changePassword,
                                             @PathVariable String email) throws UserNotFoundException {

        return ResponseEntity.status(HttpStatus.CREATED).body(forgotPasswordService.changePassword(changePassword,email));
    }

}
