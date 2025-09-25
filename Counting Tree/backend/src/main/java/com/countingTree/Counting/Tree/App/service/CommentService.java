package com.countingTree.Counting.Tree.App.service;

import java.util.List;

import com.countingTree.Counting.Tree.App.model.Comment;

public interface CommentService {

    void addComment(Comment comment);
    void updateComment(Long commentId, Comment comment);
    void deleteComment(Long commentId);
    Comment getCommentById(Long commentId);
    List<Comment> getAllComments();

}
