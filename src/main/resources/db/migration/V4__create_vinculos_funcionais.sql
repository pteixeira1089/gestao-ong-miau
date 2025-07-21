CREATE TABLE vinculos_funcionais(
    id BIGSERIAL PRIMARY KEY,
    colaborador_id BIGINT NOT NULL REFERENCES colaboradores(id) ON DELETE CASCADE,
    tipo_funcao tipo_funcao NOT NULL,
    data_admissao DATE NOT NULL,
    data_desligamento DATE,
    obs_admissao TEXT,
    obs_desligamento TEXT,
    CONSTRAINT valid_dates CHECK (
        (data_desligamento IS NULL AND obs_desligamento IS NULL) OR
        (data_desligamento IS NOT NULL AND obs_desligamento IS NOT NULL AND data_desligamento >= data_admissao)
    )
);

-- Índice para melhor performance nas buscas por colaborador_id
CREATE INDEX idx_vinculos_colaborador ON vinculos_funcionais(colaborador_id);