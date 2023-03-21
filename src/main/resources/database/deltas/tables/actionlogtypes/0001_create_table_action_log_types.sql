CREATE TABLE action_log_types
(
    id    BIGSERIAL    NOT NULL,
    title VARCHAR(128) NOT NULL,
    PRIMARY KEY (id)
) WITH (oids = false
    );

INSERT INTO action_log_types (id, title)
values (1, 'Log in');
INSERT INTO action_log_types (id, title)
values (2, 'Log out');
INSERT INTO action_log_types (id, title)
values (3, 'Password');
INSERT INTO action_log_types (id, title)
values (4, 'Endpoint call');
INSERT INTO action_log_types (id, title)
values (5, 'Buy');
INSERT INTO action_log_types (id, title)
values (6, 'Sell');
INSERT INTO action_log_types (id, title)
values (7, 'Balance');
INSERT INTO action_log_types (id, title)
values (8, 'User');
