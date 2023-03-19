CREATE TABLE users
(
    id                      BIGSERIAL                   NOT NULL,
    name                    VARCHAR(255)                NOT NULL,
    username                VARCHAR(255)                NOT NULL,
    role_id                 BIGINT                      NOT NULL,
    password                VARCHAR(255),
    active                  BOOLEAN                              DEFAULT TRUE NOT NULL,
    deleted                 BOOLEAN                              DEFAULT false NOT NULL,
    last_login              timestamptz                 NOT NULL DEFAULT now(),
    failed_attempt          INT                         NOT NULL DEFAULT 0,
    account_non_locked      BOOL                        NOT NULL DEFAULT true,
    last_ip                 varchar(255),
    password_expiry         timestamptz                 NOT NULL DEFAULT now() + interval '3 months',
    last_request            timestamptz                 NOT NULL DEFAULT now(),
    qr_code_enabled         bool                                 default true,
    qr_code_created         bool                                 default false,
    qr_code_secret          varchar(255),
    qr_code_validation_code int,
    qr_code_scratch_codes   varchar(255),
    created                 TIMESTAMP(0) WITH TIME ZONE NOT NULL DEFAULT now(),
    updated                 TIMESTAMP(0) WITH TIME ZONE NOT NULL DEFAULT now(),
    version                 INTEGER                              DEFAULT 1 NOT NULL,
    PRIMARY KEY (id)
)
    WITH (oids = false
    );


ALTER TABLE users
    ADD CONSTRAINT fk_users_roles FOREIGN KEY (role_id)
        REFERENCES roles (id);


CREATE INDEX idx_users_role_id
    ON users
        USING btree ("role_id");
