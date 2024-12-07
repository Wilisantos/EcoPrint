CREATE TABLE ponto_reciclagem_tipo_residuos (
    id SERIAL PRIMARY KEY,                         -- ID auto-incrementado para a tabela de associação
    ponto_reciclagem_id INT NOT NULL,           -- Chave estrangeira para ponto_reciclagem
    tipo_residuo_id BIGINT NOT NULL,               -- Chave estrangeira para tipo_residuos
    
    -- Define a relação com ponto_reciclagem
    CONSTRAINT fk_ponto_reciclagem FOREIGN KEY (ponto_reciclagem_id)
        REFERENCES ponto_reciclagem (id)
        ON DELETE CASCADE,

    -- Define a relação com tipo_residuos
    CONSTRAINT fk_tipo_residuo FOREIGN KEY (tipo_residuo_id)
        REFERENCES tipo_residuo (id)
        ON DELETE CASCADE,

    -- Garante que não haverá duplicação de registros
    UNIQUE (ponto_reciclagem_id, tipo_residuo_id)
);

-- Cria a tabela reciclagem_tipo_residuos
CREATE TABLE reciclagem_tipo_residuos (
    id SERIAL PRIMARY KEY,                    -- Chave primária auto-incrementada
    reciclagem_id INT NOT NULL,               -- Chave estrangeira para a tabela reciclagem
    tipo_residuo_id BIGINT NOT NULL,             -- Chave estrangeira para a tabela tipo_residuo
    descricao VARCHAR(255) NOT NULL,          -- Descrição do tipo de resíduo
    data_criacao TIMESTAMP DEFAULT NOW(),     -- Data de criação com valor padrão

    -- Chave estrangeira para reciclagem
    CONSTRAINT fk_reciclagem FOREIGN KEY (reciclagem_id)
        REFERENCES reciclagem (id)
        ON DELETE CASCADE,

    -- Chave estrangeira para tipo_residuo
    CONSTRAINT fk_tipo_residuo FOREIGN KEY (tipo_residuo_id)
        REFERENCES tipo_residuo (id)
        ON DELETE CASCADE
);
