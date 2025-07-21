CREATE TABLE roles(
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL UNIQUE CHECK (nome IN ('ROLE_ADMIN', 'ROLE_USER'))
);

--First insertion
INSERT INTO roles (nome) VALUES
                             ('ROLE_ADMIN'),
                             ('ROLE_USER');