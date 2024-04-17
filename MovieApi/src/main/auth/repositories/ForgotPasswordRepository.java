package com.movieflix.auth.repositories;

import com.movieflix.auth.entities.ForgotPassword;
import com.movieflix.auth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword,Integer> {

    @Query("""
select f from ForgotPassword f
where f.otp = :otp and f.user = :user
""")
    Optional<ForgotPassword> findByOtpAndUser(Integer otp, User user);
}
