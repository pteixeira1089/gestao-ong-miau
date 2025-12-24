CREATE TABLE animal(
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    especie_animal especie_animal NOT NULL,
    sexo CHAR(1) NOT NULL,
    pelagem VARCHAR(255) NOT NULL,
    bio TEXT,
    url_foto TEXT,
    --Campos de auditoria
    data_criacao TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);