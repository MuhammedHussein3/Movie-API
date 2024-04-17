package com.movieflix.auth.service;

import com.movieflix.auth.entities.User;
import com.movieflix.auth.repositories.UserRepository;
import com.movieflix.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {
    private final UserRepository userRepository;


    public Optional<User> findUserByEmail(String username) throws UserNotFoundException {
        return userRepository.findByEmail(username);
    }


    @Transactional
    void changePasswordUser(String email,String password){
        userRepository.changePasswordUser(email,password);
    }
}
