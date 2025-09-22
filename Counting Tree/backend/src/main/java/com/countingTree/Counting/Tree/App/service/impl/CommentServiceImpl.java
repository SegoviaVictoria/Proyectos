package com.countingTree.Counting.Tree.App.service.impl;

import com.countingTree.Counting.Tree.App.model.Comment;
import com.countingTree.Counting.Tree.App.model.Plant;
import com.countingTree.Counting.Tree.App.repository.CommentRepository;
import com.countingTree.Counting.Tree.App.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired 
	private final CommentRepository commentRepository;

	@Override
	public void addComment(Comment comment) {
        validateNewComment(comment);
		commentRepository.save(comment);
	}

	@Override
	public void deleteComment(Long commentId) {
		commentRepository.deleteById(commentId);
	}

	@Override
	public void editComment(Long commentId, Comment newComment) {
		Optional<Comment> optionalComment = commentRepository.findById(commentId);
		if (optionalComment.isPresent()) {
			Comment comment = optionalComment.get();
			comment.setText(newComment.getText());
			commentRepository.save(comment);
		}
	}

	@Override
	public Comment getComment(Long commentId) {
        Comment commentSearched = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment with ID " + commentId + " not found"));
		return commentSearched;
	}

	@Override
	public List<Comment> getAllCommentsForPlant(Long plantId) {
		return commentRepository.findAll().stream()
				.filter(comment -> comment.getPlant() != null && comment.getPlant().getPlantId().equals(plantId))
				.toList();
	}
    
    // EXTRA METHODS

    private void validateNewComment(Comment newComment) {
        if (newComment.getText() == null || newComment.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("Comment text cannot be null or empty");
        }
        if (newComment.getUser() == null || newComment.getUser().getUserId() == null) {
            throw new IllegalArgumentException("Comment must be associated with a valid user");
        }
        if (newComment.getPlant() == null || newComment.getPlant().getPlantId() == null) {
            throw new IllegalArgumentException("Comment must be associated with a valid plant");
        }
    }

}
