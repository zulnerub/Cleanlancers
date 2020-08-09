package com.simo.web.user.service;

import com.simo.web.user.model.RoleEntity;
import com.simo.web.user.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleEntity saveRole(RoleEntity role) {
        return this.roleRepository.save(role);
    }

    @Override
    public RoleEntity findRoleByName(String roleName) {
        return this.roleRepository.findRoleEntityByName(roleName);
    }
}
