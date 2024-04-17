package com.movieflix.auth.repositories;

import com.movieflix.auth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

     Optional<User> findByEmail(String username);

     @Modifying
     @Query("""
UPDATE User  u
set u.password = :password
where u.email = :email
""")
     void changePasswordUser(String email,String password);
}
