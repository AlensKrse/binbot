package com.binance.crypto.bot.api.role.repository;

import com.binance.crypto.bot.api.role.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
