package com.countingTree.Counting.Tree.App.service;

import java.util.List;

import com.countingTree.Counting.Tree.App.model.User;

public interface UserService {

    void addUser(User newUser);

    void deleteUser(Long userId);

    User getUserById(Long userId);

    void updateUser(Long userId, User user);

    List<User> getAllUsers();
}
