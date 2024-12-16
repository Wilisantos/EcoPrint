package com.ecoprint.control_center.dto;

public record ImpressaoRequestDTO(
        int produtoId ,      // ID do tipo de produto
        String cmykPredictor,
        byte[] dadosImagem,     // Array de bytes para os dados da imagem
        String descricao
) {
}