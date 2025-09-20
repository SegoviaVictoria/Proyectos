package com.countingTree.Counting.Tree.App.model;

import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(name = "text", nullable = false)
    private String text;


    // -------------------------------------------------------- RELATIONS

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "plant_id", nullable = false)
    private Plant plant;

    
    // -------------------------------------------------------- CONSTRUCTORS, GETTERS AND SETTERS

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
