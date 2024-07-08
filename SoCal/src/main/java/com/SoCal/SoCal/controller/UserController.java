package com.SoCal.SoCal.controller;

import com.SoCal.SoCal.domain.User;
import com.SoCal.SoCal.service.UserService;
import jakarta.persistence.GeneratedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/Users")
    public ResponseEntity<Iterable<User>> getAllUsers() {
        Iterable<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/Users")
    public ResponseEntity<User> createUser(@RequestBody User user){
        User newlyCreatedUser = userService.save(user);
        return new ResponseEntity<>(newlyCreatedUser, HttpStatus.CREATED);
    }

    @GetMapping("/Users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User foundUser = userService.findById(id);
        if (foundUser != null) {
            return new ResponseEntity<>(foundUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/Users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/Users/{id}")
    public ResponseEntity<User> updateUsername(@PathVariable Long id, @RequestBody String newUsername) {
        User updatedUser = userService.updateUsername(id, newUsername);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
