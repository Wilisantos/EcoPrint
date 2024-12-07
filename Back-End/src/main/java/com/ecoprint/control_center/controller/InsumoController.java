package com.ecoprint.control_center.controller;

import com.ecoprint.control_center.dto.InsumoRequestDTO;
import com.ecoprint.control_center.model.Insumo;
import com.ecoprint.control_center.repository.InsumoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/insumos")
public class InsumoController {

    @Autowired
    private InsumoRepository insumoRepository;

    // CREATE - Adiciona um novo insumo
    @PostMapping
    public ResponseEntity<Insumo> create(@RequestBody InsumoRequestDTO insumoRequestDTO) {
        Insumo insumo = Insumo.builder()
                .tipoInsumo(insumoRequestDTO.tipoInsumo())
                .descricao(insumoRequestDTO.descricao())
                .quantidade(insumoRequestDTO.quantidade())
                .build();

        insumoRepository.save(insumo);
        return ResponseEntity.status(HttpStatus.CREATED).body(insumo);
    }

    // READ - Obtém todos os insumos
    @GetMapping
    public Page<Insumo> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return insumoRepository.findAll(PageRequest.of(page, size));
    }

    // READ - Obtém um insumo por ID
    @GetMapping("/{id}")
    public ResponseEntity<Insumo> getById(@PathVariable Long id) {
        Optional<Insumo> insumoOpt = insumoRepository.findById(id);

        return insumoOpt.map(insumo -> ResponseEntity.status(HttpStatus.OK).body(insumo))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // UPDATE - Atualiza um insumo existente
    @PutMapping("/{id}")
    public ResponseEntity<Insumo> update(@PathVariable Long id, @RequestBody InsumoRequestDTO insumoRequestDTO) {
        Optional<Insumo> insumoOpt = insumoRepository.findById(id);

        if (insumoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Insumo insumo = insumoOpt.get();
        insumo.setTipoInsumo(insumoRequestDTO.tipoInsumo());
        insumo.setDescricao(insumoRequestDTO.descricao());
        insumo.setQuantidade(insumoRequestDTO.quantidade());

        insumoRepository.save(insumo);
        return ResponseEntity.status(HttpStatus.OK).body(insumo);
    }

    // DELETE - Remove um insumo por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Optional<Insumo> insumoOpt = insumoRepository.findById(id);

        if (insumoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        insumoRepository.delete(insumoOpt.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
