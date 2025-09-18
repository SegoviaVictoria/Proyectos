package com.countingTree.Counting.Tree.App.model;

public class User {

    private Long userId;
    private String name;
    private String email;
    private String password;
    private String photo;
    private Role rol;

    public User(Long userId, String name, String email, String password, String photo, Role rol) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.photo = photo;
        this.rol = rol;
    }

    public User() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Role getRol() {
        return rol;
    }

    public void setRol(Role rol) {
        this.rol = rol;
    }

}
