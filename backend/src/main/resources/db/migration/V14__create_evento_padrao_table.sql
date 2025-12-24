CREATE TABLE evento_padrao (
    evento_id BIGINT PRIMARY KEY,
    tipo tipo_evento_animal NOT NULL,
    CONSTRAINT fk_evento_padrao_evento FOREIGN KEY (evento_id) REFERENCES evento_animal(id) ON DELETE CASCADE
);
