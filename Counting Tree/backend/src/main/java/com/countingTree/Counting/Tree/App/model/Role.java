package com.countingTree.Counting.Tree.App.model;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "roles")
public class Role {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Column(name = "role_name", nullable = false, unique = true)
    private String roleName;

    @Column(name = "role_description")
    private String roleDescription;

    // ------------------------------------------------------------ RELATIONS

    @OneToMany(mappedBy = "role")
    @JsonManagedReference
    private Set<User> users = new HashSet<>();

    // ------------------------------------ CONSTRUCTORS, GETTERS AND SETTERS

    public Role(Long roleId, String roleName, String roleDescription, Set<User> users) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.roleDescription = roleDescription;
        this.users = users;
    }

    public Role() {
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

}
