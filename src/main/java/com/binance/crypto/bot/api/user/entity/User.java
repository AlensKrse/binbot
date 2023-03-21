package com.binance.crypto.bot.api.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

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

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    Date updated;

    @Version
    @Column
    Integer version;

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
