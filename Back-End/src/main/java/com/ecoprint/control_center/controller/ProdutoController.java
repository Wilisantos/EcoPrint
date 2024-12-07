package com.ecoprint.control_center.controller;

import com.ecoprint.control_center.dto.ProdutoRequestDTO;
import com.ecoprint.control_center.model.Produto;
import com.ecoprint.control_center.model.TipoProduto;
import com.ecoprint.control_center.repository.ProdutoRepository;
import com.ecoprint.control_center.repository.TipoProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private TipoProdutoRepository tipoProdutoRepository;

    // CREATE - Adiciona um novo produto
    @PostMapping
    public ResponseEntity<Produto> create(@RequestBody ProdutoRequestDTO produtoRequestDTO) {
        Optional<TipoProduto> tipoProdutoOpt = tipoProdutoRepository.findById(produtoRequestDTO.tipoProduto());

        if (tipoProdutoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }

        Produto produto = Produto.builder()
                .tipoProduto(tipoProdutoOpt.get())
                .valor(produtoRequestDTO.valor())
                .descricao(produtoRequestDTO.descricao())
                .build();

        produtoRepository.save(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produto);
    }

    // READ - Obtém todos os produtos
    @GetMapping
    public Page<Produto> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return produtoRepository.findAll(PageRequest.of(page, size));
    }

    // READ - Obtém um produto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Produto> getById(@PathVariable Integer id) {
        Optional<Produto> produtoOpt = produtoRepository.findById(id);

        if (produtoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(produtoOpt.get());
    }

    // UPDATE - Atualiza um produto existente
    @PutMapping("/{id}")
    public ResponseEntity<Produto> update(@PathVariable Integer id, @RequestBody ProdutoRequestDTO produtoRequestDTO) {
        Optional<Produto> produtoOpt = produtoRepository.findById(id);
        Optional<TipoProduto> tipoProdutoOpt = tipoProdutoRepository.findById(produtoRequestDTO.tipoProduto());

        if (produtoOpt.isEmpty() || tipoProdutoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Produto produto = produtoOpt.get();
        produto.setTipoProduto(tipoProdutoOpt.get());
        produto.setValor(produtoRequestDTO.valor());
        produto.setDescricao(produtoRequestDTO.descricao());

        produtoRepository.save(produto);
        return ResponseEntity.status(HttpStatus.OK).body(produto);
    }

    // DELETE - Remove um produto por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!produtoRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        produtoRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
