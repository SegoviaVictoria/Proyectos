package com.countingTree.Counting.Tree.App.dto;

public class CommentDTO {
    private Long commentId;
    private String text;
    private Long userId;
    private Long plantId;

    public CommentDTO() {}

    public CommentDTO(Long commentId, String text, Long userId, Long plantId) {
        this.commentId = commentId;
        this.text = text;
        this.userId = userId;
        this.plantId = plantId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPlantId() {
        return plantId;
    }

    public void setPlantId(Long plantId) {
        this.plantId = plantId;
    }
}
