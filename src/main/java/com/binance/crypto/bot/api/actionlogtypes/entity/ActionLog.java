package com.binance.crypto.bot.api.actionlogtypes.entity;

import com.binance.crypto.bot.utils.EnumUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;
import java.util.Optional;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "action_log_types")
public class ActionLog {

    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    public enum Type implements EnumUtils.IdentifiableEnum<Long> {

        LOG_IN(1L),
        LOG_OUT(2L),
        PASSWORD(3L),
        ENDPOINT_CALL(4L),
        BUY(5L),
        SELL(6L),
        BALANCE(7L),
        USER(8L);


        long id;

        @Override
        public Long getId() {
            return this.id;
        }

        public static Optional<Type> getTypeById(final long id) {
            return Arrays.stream(values()).filter(item -> item.getId().equals(id)).findFirst();
        }

        public static Type getTypeByIdStrict(final long id) {
            return Arrays.stream(values())
                    .filter(item -> item.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException(String.format("Action log type not found by ID %s", id)));
        }

    }

    @Id
    @Column
    Long id;

    @Column
    String title;

}
