package com.ecoprint.control_center.dto;

public record ImpressaoRequestDTO(
        int produtoId ,      // ID do tipo de produto
        String cmykPredictor,
        String dadosImagem,     // Array de bytes para os dados da imagem
        String descricao
) {
}