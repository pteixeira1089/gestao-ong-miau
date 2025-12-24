CREATE TABLE adocao(
    id BIGSERIAL PRIMARY KEY,
    evento_id BIGINT NOT NULL REFERENCES evento_animal(id),
    adotante_id BIGINT NOT NULL REFERENCES adotantes(id)
);