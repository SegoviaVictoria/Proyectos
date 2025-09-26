package com.countingTree.Counting.Tree.App.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.countingTree.Counting.Tree.App.model.Comment;
import com.countingTree.Counting.Tree.App.model.User;
import com.countingTree.Counting.Tree.App.model.Plant;
import com.countingTree.Counting.Tree.App.dto.CommentDTO;
import com.countingTree.Counting.Tree.App.repository.CommentRepository;
import com.countingTree.Counting.Tree.App.repository.UserRepository;
import com.countingTree.Counting.Tree.App.repository.PlantRepository;
import com.countingTree.Counting.Tree.App.service.CommentService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlantRepository plantRepository;

    @Override
    public void addComment(CommentDTO commentDTO) {
        Comment comment = toEntity(commentDTO);
        validateComment(comment);
        commentRepository.save(comment);
    }

    @Override
    public void updateComment(Long commentId, CommentDTO commentDTO) {
        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment with ID " + commentId + " not found"));
        existingComment.setText(commentDTO.getText());
        if (commentDTO.getUserId() != null) {
            User user = userRepository.findById(commentDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
            existingComment.setUser(user);
        }
        if (commentDTO.getPlantId() != null) {
            Plant plant = plantRepository.findById(commentDTO.getPlantId())
                .orElseThrow(() -> new EntityNotFoundException("Plant not found"));
            existingComment.setPlant(plant);
        }
        validateComment(existingComment);
        commentRepository.save(existingComment);
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public CommentDTO getCommentDTOById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment with ID " + commentId + " not found"));
        return toDTO(comment);
    }

    @Override
    public List<CommentDTO> getAllCommentDTOs() {
        return commentRepository.findAll().stream().map(this::toDTO).toList();
    }

    // --------------------------------------------------------- EXTRA METHODS

    private CommentDTO toDTO(Comment comment) {
        return new CommentDTO(
            comment.getCommentId(),
            comment.getText(),
            comment.getUser() != null ? comment.getUser().getUserId() : null,
            comment.getPlant() != null ? comment.getPlant().getPlantId() : null
        );
    }

    private Comment toEntity(CommentDTO dto) {
        User user = userRepository.findById(dto.getUserId())
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Plant plant = plantRepository.findById(dto.getPlantId())
            .orElseThrow(() -> new EntityNotFoundException("Plant not found"));
        Comment comment = new Comment();
        comment.setCommentId(dto.getCommentId());
        comment.setText(dto.getText());
        comment.setUser(user);
        comment.setPlant(plant);
        return comment;
    }

    private void validateComment(Comment comment) {
        if (comment.getText() == null || comment.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("Comment text cannot be null or empty");
        }
        if (comment.getUser() == null || comment.getUser().getUserId() == null) {
            throw new IllegalArgumentException("Comment must be associated with a valid user");
        }
        if (comment.getPlant() == null || comment.getPlant().getPlantId() == null) {
            throw new IllegalArgumentException("Comment must be associated with a valid plant");
        }
    }
}

}
