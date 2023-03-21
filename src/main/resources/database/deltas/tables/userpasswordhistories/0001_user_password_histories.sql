create table user_password_histories
(
    id        BIGSERIAL    NOT NULL,
    user_id   BIGINT       NOT NULL,
    password  VARCHAR(255) NOT NULL,
    created   timestamptz  NOT NULL DEFAULT now(),
    updated   timestamptz  NOT NULL DEFAULT now(),
    "version" int4         NOT NULL DEFAULT 1,
    CONSTRAINT fk_user_password_histories_user FOREIGN KEY (user_id)
        REFERENCES users (id),
    PRIMARY KEY (id)
);


CREATE INDEX idx_user_password_histories_user
    ON user_password_histories
        USING btree ("user_id");

insert into user_password_histories (user_id, password)
select id, password
from users;