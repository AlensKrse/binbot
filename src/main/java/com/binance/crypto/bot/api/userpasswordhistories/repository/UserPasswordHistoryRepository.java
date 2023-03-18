package com.binance.crypto.bot.api.userpasswordhistories.repository;

import com.binance.crypto.bot.api.userpasswordhistories.entity.UserPasswordHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPasswordHistoryRepository extends JpaRepository<UserPasswordHistory, Long> {

	List<UserPasswordHistory> findByUserIdOrderByCreatedDesc(long userId, Pageable pageable);
}
