CREATE TABLE insumo (
    id SERIAL PRIMARY KEY,                -- Coluna auto-incrementada para o ID
    tipo_insumo VARCHAR(255) NOT NULL,    -- Tipo de insumo
    descricao VARCHAR(255) NOT NULL,      -- Descrição do insumo
    quantidade BIGINT NOT NULL            -- Quantidade disponível
);

CREATE TABLE impressao (
    id SERIAL PRIMARY KEY,                -- Coluna auto-incrementada para o ID
    custo BIGINT NOT NULL,                -- Custo da impressão
    insumos_utilizados BIGINT[] NOT NULL  -- Lista de insumos utilizados
);
