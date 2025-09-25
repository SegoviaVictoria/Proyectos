package com.countingTree.Counting.Tree.App.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.countingTree.Counting.Tree.App.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

    User findByEmail(String email);
    
}
