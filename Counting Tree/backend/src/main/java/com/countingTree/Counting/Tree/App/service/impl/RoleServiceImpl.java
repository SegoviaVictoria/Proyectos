package com.countingTree.Counting.Tree.App.service.impl;

import com.countingTree.Counting.Tree.App.model.Role;
import com.countingTree.Counting.Tree.App.repository.RoleRepository;
import com.countingTree.Counting.Tree.App.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role getRoleById(Long roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role with ID " + roleId + " not found."));
    }

    @Override
    public void addRole(Role newRole) {
        // Validar Role !!!
        roleRepository.save(newRole);
    }

    @Override
    public void updateRole(Long roleId, Role role) {
        Role existingRole = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role with ID " + roleId + " not found."));
        existingRole.setRoleName(role.getRoleName());
        existingRole.setRoleDescription(role.getRoleDescription());

        roleRepository.save(existingRole);
    }
    
    @Override
    public void deleteRole(Long roleId) {
        Role existingRole = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role with ID " + roleId + " not found."));
        if (existingRole.getUser().isEmpty()) {
            rolesRepository.delete(roleId);
        }
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
