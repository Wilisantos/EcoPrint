package com.ecoprint.control_center.controller;

import com.ecoprint.control_center.dto.ReciclagemRequestDTO;
import com.ecoprint.control_center.model.Reciclagem;
import com.ecoprint.control_center.repository.ReciclagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/reciclagens")
public class ReciclagemController {

    @Autowired
    private ReciclagemRepository reciclagemRepository;

    // CREATE - Adiciona uma nova reciclagem
    @PostMapping
    public ResponseEntity<Reciclagem> create(@RequestBody ReciclagemRequestDTO reciclagemRequestDTO) {
        Reciclagem reciclagem = Reciclagem.builder()
                .tipoResiduos(reciclagemRequestDTO.tipoResiduos())
                .pontoReciclagem(reciclagemRequestDTO.pontoReciclagem())
                .valorGanho(reciclagemRequestDTO.valorGanho())
                .build();

        reciclagemRepository.save(reciclagem);

        return ResponseEntity.status(HttpStatus.CREATED).body(reciclagem);
    }

    // READ - Obtém todas as reciclagens
    @GetMapping
    public Page<Reciclagem> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return reciclagemRepository.findAll(PageRequest.of(page, size));
    }

    // READ - Obtém uma reciclagem por ID
    @GetMapping("/{id}")
    public ResponseEntity<Reciclagem> getReciclagemById(@PathVariable Integer id) {
        Optional<Reciclagem> reciclagemOpt = reciclagemRepository.findById(id);

        if (reciclagemOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(reciclagemOpt.get());
    }

    // UPDATE - Atualiza uma reciclagem
    @PutMapping("/{id}")
    public ResponseEntity<Reciclagem> updateReciclagem(@PathVariable Integer id, @RequestBody ReciclagemRequestDTO reciclagemRequestDTO) {
        Optional<Reciclagem> reciclagemOpt = reciclagemRepository.findById(id);

        if (reciclagemOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Reciclagem reciclagem = reciclagemOpt.get();
        reciclagem.setTipoResiduos(reciclagemRequestDTO.tipoResiduos());
        reciclagem.setPontoReciclagem(reciclagemRequestDTO.pontoReciclagem());
        reciclagem.setValorGanho(reciclagemRequestDTO.valorGanho());

        reciclagemRepository.save(reciclagem);

        return ResponseEntity.status(HttpStatus.OK).body(reciclagem);
    }

    // DELETE - Remove uma reciclagem por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReciclagem(@PathVariable Integer id) {
        Optional<Reciclagem> reciclagemOpt = reciclagemRepository.findById(id);

        if (reciclagemOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        reciclagemRepository.delete(reciclagemOpt.get());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
