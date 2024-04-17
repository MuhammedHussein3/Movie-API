package com.movieflix.auth.service;

import com.movieflix.auth.entities.ForgotPassword;
import com.movieflix.auth.entities.User;
import com.movieflix.auth.repositories.ForgotPasswordRepository;
import com.movieflix.auth.utils.ChangePassword;
import com.movieflix.dto.MailBody;
import com.movieflix.exception.OtpInvalidException;
import com.movieflix.exception.UserNotFoundException;
import com.movieflix.service.MailService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ForgotPasswordService {

    private final ForgotPasswordRepository forgotPasswordRepository;
    private final UserServiceImpl userService;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    public String verifyEmail(String email) throws UserNotFoundException {
        User user = userService.findUserByEmail(email).
                orElseThrow(()-> new UserNotFoundException(String.format("User not found with %s email",email)));

        MailBody mailBody = MailBody.builder()
                .to(email)
                .text("This is the otp for your forgot password request : "+otpGenerator())
                .subject("OTP for forgot password")
                 .build();

        ForgotPassword forgotPassword = ForgotPassword.builder()
                .otp(otpGenerator())
                .expirationTime(new Date(System.currentTimeMillis() * 70 * 1000))
                .user(user)
                .build();

        mailService.sendMail(mailBody);

        forgotPasswordRepository.save(forgotPassword);

        return String.format("Email %s sent for verification ",email);
    }

    @Transactional
    public String verifyOtp(Integer otp , String email) throws UserNotFoundException {
        User user = userService.findUserByEmail(email).
                orElseThrow(()-> new UserNotFoundException(String.format("User not found with %s email",email)));

      ForgotPassword fr = forgotPasswordRepository.findByOtpAndUser(otp , user)
              .orElseThrow(()-> new OtpInvalidException("Invalid OTP for Email "+ email));

      if (fr.getExpirationTime().before(Date.from(Instant.now()))) {
          forgotPasswordRepository.deleteById(fr.getId());
          throw new OtpInvalidException("OTP Verified");
      }
      return String.format("OTP %s is valid",otp);
    }

    @Transactional
    public String changePassword(ChangePassword changePassword , String email) throws UserNotFoundException {

        if (!Objects.equals(changePassword.password(),changePassword.repeatPassword())){
            return "Please enter the password again because not are the same";
        }
        User user = userService.findUserByEmail(email).
                 orElseThrow(()-> new UserNotFoundException(String.format("User not found with %s email",email)));

        String passwordEncoderToReplaceInDatabase  = passwordEncoder.encode(changePassword.password());
        userService.changePasswordUser(email,passwordEncoderToReplaceInDatabase);

        forgotPasswordRepository.deleteById(user.getForgotPassword().getId());
        return "Successfully, Password Changed";
    }

    private Integer otpGenerator(){
        Random random = new Random();
        return random.nextInt((int)1e7,((int)1e8)-1);
    }
}
