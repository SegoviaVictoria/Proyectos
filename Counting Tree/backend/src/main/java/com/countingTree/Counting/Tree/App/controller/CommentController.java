package com.countingTree.Counting.Tree.App.controller;

import com.countingTree.Counting.Tree.App.dto.CommentDTO;
import com.countingTree.Counting.Tree.App.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getCommentDTOById(id));
    }

    @PostMapping
    public ResponseEntity<Void> addComment(@RequestBody CommentDTO commentDTO) {
        commentService.addComment(commentDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateComment(@PathVariable Long id, @RequestBody CommentDTO commentDTO) {
        commentService.updateComment(id, commentDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<CommentDTO>> getAllComments() {
        return ResponseEntity.ok(commentService.getAllCommentDTOs());
    }
}
