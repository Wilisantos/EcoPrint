CREATE TABLE tipo_residuo (
    id SERIAL PRIMARY KEY,              -- Chave primária com auto-incremento
    tipo VARCHAR(255) NOT NULL UNIQUE,   -- Coluna tipo, não nula e única
    descricao VARCHAR(255) NOT NULL UNIQUE -- Coluna descricao, não nula e única
);

CREATE TABLE residuo (
    id SERIAL PRIMARY KEY,               -- Chave primária com auto-incremento
    tipo_residuo_id INT NOT NULL,         -- Coluna tipo_residuo_id (chave estrangeira)
    quantidade INT NOT NULL,              -- Coluna quantidade (não nula)
    CONSTRAINT fk_tipo_residuo           -- Definindo o nome da chave estrangeira
        FOREIGN KEY (tipo_residuo_id)    -- Relacionando a chave estrangeira com a tabela tipo_residuo
        REFERENCES tipo_residuo(id)      -- A coluna tipo_residuo_id faz referência à coluna id da tabela tipo_residuo
        ON DELETE CASCADE                -- Se um tipo de resíduo for excluído, os resíduos relacionados também serão excluídos
);