package com.binance.crypto.bot.api.useractionlog.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Entity
@Table(name = "user_action_logs")
public class UserActionLog {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    Long userId;

    @Column
    Long actionId;

    @Column
    String action;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    Date updated;

    @Version
    @Column
    Integer version;

    public UserActionLog(final Long userId, final Long actionId, final String action) {
        this.userId = userId;
        this.actionId = actionId;
        this.action = action;
    }

    @PrePersist
    protected void onCreate() {
        created = new Date();
        updated = created;
    }

    @PreUpdate
    protected void onUpdate() {
        updated = new Date();
    }

}
