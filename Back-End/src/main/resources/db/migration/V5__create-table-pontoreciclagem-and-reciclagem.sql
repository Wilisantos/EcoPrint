CREATE TABLE ponto_reciclagem (
    id SERIAL PRIMARY KEY,                -- Coluna auto-incrementada para o ID
    tipo_residuos BIGINT[] NOT NULL,      -- Lista de tipos de resíduos aceitos
    descricao VARCHAR(255) NOT NULL       -- Descrição do ponto de reciclagem
);

CREATE TABLE reciclagem (
    id SERIAL PRIMARY KEY,                -- Coluna auto-incrementada para o ID
    tipo_residuos BIGINT[] NOT NULL,      -- Lista de tipos de resíduos reciclados
    ponto_reciclagem VARCHAR(255) NOT NULL, -- Nome/identificação do ponto de reciclagem
    valor_ganho BIGINT NOT NULL           -- Valor ganho com a reciclagem
);