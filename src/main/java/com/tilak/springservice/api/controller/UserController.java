package com.tilak.springservice.api.controller;

import com.tilak.springservice.api.model.User;
import com.tilak.springservice.api.repository.UserRepository;
import exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public String saveUser(@RequestBody User user){
        userRepository.save(user);
        return "User registered successfully!";
    }

    @GetMapping(path="", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") int userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found for this id "+userId));
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") int userId) throws ResourceNotFoundException{
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found for this id "+userId));
        userRepository.delete(user);
        return "User deleted successfully!";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable("id") int userId, @RequestBody User userDetail) throws ResourceNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found for this id " + userId));
        user.setName(userDetail.getName());
        user.setEmail(userDetail.getEmail());
        user.setPassword(userDetail.getPassword());
        user.setDob(userDetail.getDob());
        user.setGender(userDetail.getGender());
        userRepository.save(user);
        return "User updated successfully!";
    }
}
