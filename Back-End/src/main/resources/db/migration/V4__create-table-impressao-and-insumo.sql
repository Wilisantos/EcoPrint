CREATE TABLE insumo (
    id SERIAL PRIMARY KEY,                -- Coluna auto-incrementada para o ID
    tipo_insumo VARCHAR(255) NOT NULL,    -- Tipo de insumo
    descricao VARCHAR(255) NOT NULL,      -- Descrição do insumo
    quantidade BIGINT NOT NULL            -- Quantidade disponível
);

CREATE TABLE impressao (
    id SERIAL PRIMARY KEY,               -- Coluna auto-incrementada para o ID
    descricao VARCHAR(255) NOT NULL,     -- Descrição da impressão
    produto_id INT NOT NULL,
    iccModel VARCHAR(255),               -- Modelo ICC
    dadosImagem TEXT,                     -- Dados da imagem (alterado para OID, tipo adequado para grandes objetos binários)
    cmykPredictor VARCHAR(255)          -- Previsor de CMYK
);
