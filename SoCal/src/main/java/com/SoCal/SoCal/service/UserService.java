package com.SoCal.SoCal.service;

import com.SoCal.SoCal.domain.User;
import com.SoCal.SoCal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }


    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public User updateUsername(Long id, String newUsername) {
        Optional<User> userToBeUpdated = userRepository.findById(id);
        if (userToBeUpdated.isPresent()) {
            User user = userToBeUpdated.get();
            user.setUsername(newUsername);
            return userRepository.save(user);
        } else {
            return null;
        }
    }
}