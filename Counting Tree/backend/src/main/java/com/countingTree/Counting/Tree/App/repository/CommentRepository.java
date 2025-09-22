package com.countingTree.Counting.Tree.App.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.countingTree.Counting.Tree.App.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    
}
