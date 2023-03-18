package com.binance.crypto.bot.api.user.repository;

import com.binance.crypto.bot.api.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findOneByUsername(String username);

	Optional<User> findOneByUsernameAndActiveIsTrue(String username);

	boolean existsByUsername(String username);

}
