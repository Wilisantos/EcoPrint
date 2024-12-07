package com.ecoprint.control_center.controller.mapper;

import com.ecoprint.control_center.dto.ProdutoRequestDTO;
import com.ecoprint.control_center.model.Produto;
import com.ecoprint.control_center.model.TipoProduto;
import com.ecoprint.control_center.repository.ProdutoRepository;
import com.ecoprint.control_center.repository.TipoProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProdutoMapper {

    private final ProdutoRepository produtoRepository;
    private final TipoProdutoRepository tipoProdutoRepository;

    // Criação de um novo Produto
    public Produto toModel(ProdutoRequestDTO produtoRequestDTO) {
        TipoProduto tipoProduto = tipoProdutoRepository.findById(produtoRequestDTO.tipoProduto())
                .orElseThrow(() -> new IllegalArgumentException("TipoProduto com ID " + produtoRequestDTO.tipoProduto() + " não encontrado"));

        return Produto.builder()
                .tipoProduto(tipoProduto)
                .valor(produtoRequestDTO.valor())
                .descricao(produtoRequestDTO.descricao())
                .build();
    }

    // Atualização de um Produto existente
    public Produto toModel(Integer id, ProdutoRequestDTO produtoRequestDTO) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto com ID " + id + " não encontrado"));

        TipoProduto tipoProduto = tipoProdutoRepository.findById(produtoRequestDTO.tipoProduto())
                .orElseThrow(() -> new IllegalArgumentException("TipoProduto com ID " + produtoRequestDTO.tipoProduto() + " não encontrado"));

        produto.setTipoProduto(tipoProduto);
        produto.setValor(produtoRequestDTO.valor());
        produto.setDescricao(produtoRequestDTO.descricao());

        return produto;
    }
}
