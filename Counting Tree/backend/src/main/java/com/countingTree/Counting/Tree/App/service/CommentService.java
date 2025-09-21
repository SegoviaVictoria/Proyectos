package com.countingTree.Counting.Tree.App.service;

import java.util.List;

import com.countingTree.Counting.Tree.App.model.Comment;

public interface CommentService {

    void addComment(Comment comment);

    void deleteComment(Long commentId);

    void editComment(Long commentId, Comment newComment);

    Comment getComment(Long commentId);

    List<Comment> getAllCommentsForPlant(Long plantId);

}
