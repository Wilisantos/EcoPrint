package com.ecoprint.control_center.controller.mapper;

import com.ecoprint.control_center.dto.ImpressaoRequestDTO;
import com.ecoprint.control_center.model.Impressao;
import com.ecoprint.control_center.model.Produto;
import com.ecoprint.control_center.model.TipoImpressora;
import com.ecoprint.control_center.repository.ImpressaoRepository;
import com.ecoprint.control_center.repository.ProdutoRepository;
import com.ecoprint.control_center.repository.TipoProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImpressaoMapper {


    private final ProdutoRepository produtoRepository;

    // Mapeia ImpressaoDTO para o modelo Impressao
    public Impressao toModel(ImpressaoRequestDTO impressaoDTO) {

        Produto produto = produtoRepository.findById(impressaoDTO.produtoId())
                .orElseThrow(() -> new IllegalArgumentException("Impressão não encontrada"));

        // Usando o Builder para criar uma nova Impressao
        return Impressao.builder()
                .produto(produto)
                .descricao(impressaoDTO.descricao())
                .cmykPredictor(impressaoDTO.cmykPredictor())
                .iccModel(produtoRepository.findIccModelById(impressaoDTO.produtoId()))
                .dadosImagem(impressaoDTO.dadosImagem())
                .build();
    }

}
