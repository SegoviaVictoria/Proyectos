package com.countingTree.Counting.Tree.App.repository;

import javax.xml.stream.events.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    
}
