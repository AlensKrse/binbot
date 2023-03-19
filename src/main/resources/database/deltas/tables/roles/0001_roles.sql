CREATE TABLE roles
(
	id      BIGSERIAL                   NOT NULL,
	name    VARCHAR(255)                NOT NULL,
	PRIMARY KEY (id)
)
	WITH (oids = false
	);

INSERT INTO roles (id, name)
VALUES (1, 'ROLE_ADMINISTRATOR'),
	   (2, 'ROLE_CLIENT');

