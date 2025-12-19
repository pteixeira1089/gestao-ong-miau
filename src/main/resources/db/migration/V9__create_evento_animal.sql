CREATE TABLE evento_animal(
    id BIGSERIAL PRIMARY KEY,
    animal_id BIGINT NOT NULL REFERENCES animal(id) ON DELETE CASCADE,
    data_evento DATE NOT NULL,
    obs_evento TEXT,
    --Campos de auditoria
    data_criacao TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);