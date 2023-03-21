package com.binance.crypto.bot.api.role.service;

import com.binance.crypto.bot.api.role.entity.Role;
import com.binance.crypto.bot.api.role.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Role getRoleById(final long roleId) {
        return roleRepository.findById(roleId).orElseThrow(() -> new EntityNotFoundException(String.format("Role is not found by role id: '%d'", roleId)));
    }
}
