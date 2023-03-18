CREATE TABLE user_action_logs
(
    id        BIGSERIAL                   NOT NULL,
    user_id   BIGINT                      NOT NULL,
    action_id BIGINT                      NOT NULL,
    action    varchar(1024)               NOT NULL,
    created   TIMESTAMP(0) WITH TIME ZONE NOT NULL DEFAULT now(),
    updated   TIMESTAMP(0) WITH TIME ZONE NOT NULL DEFAULT now(),
    version   INTEGER                              DEFAULT 1 NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_user_action_log_user_id FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_user_action_log_action_id FOREIGN KEY (action_id) REFERENCES action_log_types (id)
);

CREATE INDEX idx_user_action_log_user_id
    ON user_action_logs
        USING btree ("user_id");

CREATE INDEX idx_user_action_log_action_id
    ON user_action_logs
        USING btree ("action_id");