package com.countingTree.Counting.Tree.App.model;

public class Comment {
    
    private Long commentId;
    private String text;

    private User user;
    private Plant plant;

    public Comment(Long commentId, String text, User user, Plant plant) {
        this.commentId = commentId;
        this.text = text;
        this.user = user;
        this.plant = plant;
    }

    public Comment() {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    
}
