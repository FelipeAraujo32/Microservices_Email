package com.ms.user.services;

import org.springframework.stereotype.Service;

import com.ms.user.Producer.UserProducer;
import com.ms.user.models.User;
import com.ms.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    final UserRepository userRepository;
    final UserProducer userProducer;

    public UserService(UserRepository userRepository, UserProducer userProducer) {
        this.userRepository = userRepository;
        this.userProducer = userProducer;
    }

    @Transactional
    public User save(User user) {
        user = userRepository.save(user);
        userProducer.publishMessageEmail(user);
        return user;
    }
}
