CREATE TABLE adotantes(
    id BIGSERIAL PRIMARY KEY,
    nome TEXT NOT NULL,
    telefone TEXT,
    email TEXT,
    documento_identificacao TEXT NOT NULL,
    endereco_id BIGINT NOT NULL REFERENCES endereco(id),
    observacoes TEXT,
    foto_url TEXT
);
