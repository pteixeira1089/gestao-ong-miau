CREATE TABLE colaboradores_roles(
    colaborador_id BIGINT NOT NULL REFERENCES colaboradores(id) ON DELETE CASCADE,
    role_id BIGINT NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
    PRIMARY KEY (colaborador_id, role_id)
);