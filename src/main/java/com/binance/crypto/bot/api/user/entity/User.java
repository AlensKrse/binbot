package com.binance.crypto.bot.api.user.entity;

import com.binance.crypto.bot.api.common.AuditableEntity;
import com.binance.crypto.bot.api.roles.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users")
public class User extends AuditableEntity {

    public User() {
        this.active = true;
        this.deleted = false;
        this.qrCodeEnabled = true;
        this.qrCodeCreated = false;
    }

    @Column
    String name;

    @Column
    String username;

    @Column
    Long roleId;

    @JsonIgnore
    @Column
    String password;

    @Column
    Boolean active;

    @Column
    Boolean deleted;

    @Column
    Date lastLogin;

    @Column
    Integer failedAttempt;

    @Column
    Boolean accountNonLocked;

    @Column
    String lastIp;

    @Column
    Date passwordExpiry;

    @Column
    Date lastRequest;

    @Column
    Boolean qrCodeEnabled;

    @Column
    String qrCodeSecret;

    @Column
    Integer qrCodeValidationCode;

    @Column
    String qrCodeScratchCodes;

    @Column
    Boolean qrCodeCreated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleId", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    Role role;

}
