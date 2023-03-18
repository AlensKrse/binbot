package com.binance.crypto.bot.api.useractionlog.repository;


import com.binance.crypto.bot.api.useractionlog.entity.UserActionLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserActionLogRepository extends JpaRepository<UserActionLog, Long> {

}
