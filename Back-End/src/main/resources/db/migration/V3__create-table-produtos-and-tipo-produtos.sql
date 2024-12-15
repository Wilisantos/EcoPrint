CREATE TABLE tipo_produto (
    id SERIAL PRIMARY KEY,                -- Coluna auto-incrementada para o ID
    tipo VARCHAR(255) NOT NULL UNIQUE,     -- Coluna única para o tipo
    iccModel VARCHAR(255)
);

CREATE TABLE produto (
    id SERIAL PRIMARY KEY,                -- Coluna auto-incrementada para o ID
    tipo_produto_id INT NOT NULL,      -- Chave estrangeira referenciando tipo_produto
    descricao VARCHAR(255) NOT NULL,     -- Coluna para descrição
    valor BIGINT NOT NULL,               -- Coluna para valor
    CONSTRAINT fk_tipo_produto FOREIGN KEY (tipo_produto_id)
        REFERENCES tipo_produto (id)      -- Define a relação entre produto e tipo_produto
        ON DELETE CASCADE                 -- Remove os produtos se o tipo for deletado
);
