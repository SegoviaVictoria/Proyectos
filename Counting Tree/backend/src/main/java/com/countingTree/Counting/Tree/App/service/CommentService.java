package com.countingTree.Counting.Tree.App.service;

import java.util.List;

import com.countingTree.Counting.Tree.App.dto.CommentDTO;

public interface CommentService {

    void addComment(CommentDTO commentDTO);
    void updateComment(Long commentId, CommentDTO commentDTO);
    void deleteComment(Long commentId);
    CommentDTO getCommentDTOById(Long commentId);
    List<CommentDTO> getAllCommentDTOs();

}
