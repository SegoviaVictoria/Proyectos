package com.countingTree.Counting.Tree.App.model;

import java.util.Set;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    @JsonIgnore
    private String password;

    @Column(name = "photo")
    private String photo;

    // -------------------------------------------------------- RELATIONS

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    @JsonBackReference
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Plant> plantsRegistered;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Alert> alertsCreated;

    @OneToMany(mappedBy = "resolver", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Alert> alertsResolved;

    // Commented out to avoid cyclic dependency issues
    // private Set<Comment> commentsMade;
    // private Set<Log> logsPerformed;
    // private Set<PlantPhoto> plantPhotosUploaded;
    // private Set<Export> exportsMade;

    // -------------------------------------------------------- CONSTRUCTORS, GETTERS AND SETTERS

    public User(Long userId, String firstName, String lastName, String email, String password, String photo, Role role, Set<Plant> plantsRegistered, Set<Alert> alertsCreated, Set<Alert> alertsResolved) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.photo = photo;
        this.role = role;
        this.plantsRegistered = plantsRegistered;
        this.alertsCreated = alertsCreated;
        this.alertsResolved = alertsResolved;
    }

    public User() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Plant> getPlantsRegistered() {
        return plantsRegistered;
    }

    public void setPlantsRegistered(Set<Plant> plantsRegistered) {
        this.plantsRegistered = plantsRegistered;
    }

    public Set<Alert> getAlertsCreated() {
        return alertsCreated;
    }

    public void setAlertsCreated(Set<Alert> alertsCreated) {
        this.alertsCreated = alertsCreated;
    }

    public Set<Alert> getAlertsResolved() {
        return alertsResolved;
    }

    public void setAlertsResolved(Set<Alert> alertsResolved) {
        this.alertsResolved = alertsResolved;
    }

}
