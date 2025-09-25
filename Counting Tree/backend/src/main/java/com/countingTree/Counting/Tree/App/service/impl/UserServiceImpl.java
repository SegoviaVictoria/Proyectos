package com.countingTree.Counting.Tree.App.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.countingTree.Counting.Tree.App.model.User;
import com.countingTree.Counting.Tree.App.repository.UserRepository;
import com.countingTree.Counting.Tree.App.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addUser(User newUser) {
        validateUser(newUser);

        User existingUserMail = userRepository.findByEmail(newUser.getEmail());
        if (existingUserMail != null) {
            throw new IllegalArgumentException("Email " + newUser.getEmail() + " is already in use.");
        } else {
            userRepository.save(newUser);
        }
    }

    @Override
    public void deleteUser(Long userId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " not found."));
        userRepository.deleteById(userId);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " not found."));
    }

    @Override
    public void updateUser(Long userId, User user) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " not found."));
        validateUser(user);
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setPhoto(user.getPhoto());
        existingUser.setRole(user.getRole());
        existingUser.setPlantsRegistered(user.getPlantsRegistered());
        existingUser.setAlertsCreated(user.getAlertsCreated());
        existingUser.setAlertsResolved(user.getAlertsResolved());
        userRepository.save(existingUser);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // EXTRA METHODS
    private void validateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User must not be null");
        }
        if (user.getFirstName() == null || user.getFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("First name must not be null or empty");
        }
        if (user.getLastName() == null || user.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("Last name must not be null or empty");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email must not be null or empty");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password must not be null or empty");
        }
    }
}
