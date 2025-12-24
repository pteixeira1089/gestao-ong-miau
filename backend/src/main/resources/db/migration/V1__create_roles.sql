CREATE TABLE roles(
    id BIGSERIAL PRIMARY KEY,
    role VARCHAR(50) NOT NULL UNIQUE CHECK (role IN ('ROLE_ADMIN', 'ROLE_USER'))
);

--First insertion
INSERT INTO roles (role) VALUES
                             ('ROLE_ADMIN'),
                             ('ROLE_USER');
