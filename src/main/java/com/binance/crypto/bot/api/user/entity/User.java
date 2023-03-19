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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
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
    private String name;

    @Column
    private String username;

    @Column
    private Long roleId;

    @JsonIgnore
    @Column
    private String password;

    @Column
    private Boolean active;

    @Column
    private Boolean deleted;

    @Column
    private Date lastLogin;

    @Column
    private Integer failedAttempt;

    @Column
    private Boolean accountNonLocked;

    @Column
    private String lastIp;

    @Column
    private Date passwordExpiry;

    @Column
    private Date lastRequest;

    @Column
    private Boolean qrCodeEnabled;

    @Column
    private String qrCodeSecret;

    @Column
    private Integer qrCodeValidationCode;

    @Column
    private String qrCodeScratchCodes;

    @Column
    private Boolean qrCodeCreated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleId", referencedColumnName = "id", insertable = false, updatable = false)
    @JsonIgnore
    private Role role;

}
