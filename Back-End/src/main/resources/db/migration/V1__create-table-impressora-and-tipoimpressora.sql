CREATE TABLE tipo_impressora (
    id SERIAL PRIMARY KEY,
    tipo VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE impressora (
    id SERIAL PRIMARY KEY,
    tipo_impressora_id INT NOT NULL,
    modelo VARCHAR(255) NOT NULL,
    capacidade BIGINT NOT NULL,
    CONSTRAINT fk_tipo_impressora
        FOREIGN KEY (tipo_impressora_id)
        REFERENCES tipo_impressora(id)
        ON DELETE CASCADE
);