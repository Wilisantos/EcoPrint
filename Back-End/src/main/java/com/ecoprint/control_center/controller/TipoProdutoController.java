package com.ecoprint.control_center.controller;

import com.ecoprint.control_center.dto.TipoProdutoRequestDTO;
import com.ecoprint.control_center.model.TipoProduto;
import com.ecoprint.control_center.repository.TipoProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/tipos-produtos")
public class TipoProdutoController {

    @Autowired
    private TipoProdutoRepository tipoProdutoRepository;

    // CREATE - Adiciona um novo tipo de produto
    @PostMapping
    public ResponseEntity<TipoProduto> create(@RequestBody TipoProdutoRequestDTO tipoProdutoRequestDTO) {
        TipoProduto tipoProduto = TipoProduto.builder().tipo(tipoProdutoRequestDTO.tipo()).build();
        tipoProdutoRepository.save(tipoProduto);
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoProduto);
    }

    // READ - Lista todos os tipos de produtos
    @GetMapping
    public ResponseEntity<Iterable<TipoProduto>> getAll() {
        return ResponseEntity.ok(tipoProdutoRepository.findAll());
    }

    // READ - Obtém um tipo de produto por ID
    @GetMapping("/{id}")
    public ResponseEntity<TipoProduto> getById(@PathVariable int id) {
        Optional<TipoProduto> tipoProdutoOpt = tipoProdutoRepository.findById(id);

        return tipoProdutoOpt
                .map(tipoProduto -> ResponseEntity.ok(tipoProduto))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    // UPDATE - Atualiza um tipo de produto por ID
    @PutMapping("/{id}")
    public ResponseEntity<TipoProduto> update(@PathVariable int id, @RequestBody TipoProdutoRequestDTO tipoProdutoRequestDTO) {
        Optional<TipoProduto> tipoProdutoOpt = tipoProdutoRepository.findById(id);

        if (tipoProdutoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        TipoProduto tipoProduto = tipoProdutoOpt.get();
        tipoProduto.setTipo(tipoProdutoRequestDTO.tipo());
        tipoProdutoRepository.save(tipoProduto);

        return ResponseEntity.ok(tipoProduto);
    }

    // DELETE - Remove um tipo de produto por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (!tipoProdutoRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        tipoProdutoRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
