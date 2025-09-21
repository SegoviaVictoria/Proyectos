package com.countingTree.Counting.Tree.App.service;

import java.util.List;

import com.countingTree.Counting.Tree.App.model.Role;

public interface RoleService {
    
    Role getRoleById(Long roleId);

    void addRole(Role newRole);

    void updateRole(Long roleId, Role role);

    void deleteRole(Long roleId);

    List<Role> getAllRoles();

}
