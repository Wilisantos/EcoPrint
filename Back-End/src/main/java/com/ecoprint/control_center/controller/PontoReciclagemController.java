package com.ecoprint.control_center.controller;

import com.ecoprint.control_center.dto.PontoReciclagemRequestDTO;
import com.ecoprint.control_center.model.PontoReciclagem;
import com.ecoprint.control_center.repository.PontoReciclagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/pontos-reciclagem")
public class PontoReciclagemController {

    @Autowired
    private PontoReciclagemRepository pontoReciclagemRepository;

    // CREATE - Adiciona um novo ponto de reciclagem
    @PostMapping
    public ResponseEntity<PontoReciclagem> create(@RequestBody PontoReciclagemRequestDTO pontoReciclagemRequestDTO) {
        PontoReciclagem pontoReciclagem = PontoReciclagem.builder()
                .tipoResiduos(pontoReciclagemRequestDTO.tipoResiduos())
                .descricao(pontoReciclagemRequestDTO.descricao())
                .build();

        pontoReciclagemRepository.save(pontoReciclagem);

        return ResponseEntity.status(HttpStatus.CREATED).body(pontoReciclagem);
    }

    // READ - Obtém todos os pontos de reciclagem
    @GetMapping
    public Page<PontoReciclagem> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return pontoReciclagemRepository.findAll(PageRequest.of(page, size));
    }

    // READ - Obtém um ponto de reciclagem por ID
    @GetMapping("/{id}")
    public ResponseEntity<PontoReciclagem> getPontoReciclagemById(@PathVariable Integer id) {
        Optional<PontoReciclagem> pontoReciclagemOpt = pontoReciclagemRepository.findById(id);

        if (pontoReciclagemOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(pontoReciclagemOpt.get());
    }

    // UPDATE - Atualiza um ponto de reciclagem
    @PutMapping("/{id}")
    public ResponseEntity<PontoReciclagem> updatePontoReciclagem(@PathVariable Integer id, @RequestBody PontoReciclagemRequestDTO pontoReciclagemRequestDTO) {
        Optional<PontoReciclagem> pontoReciclagemOpt = pontoReciclagemRepository.findById(id);

        if (pontoReciclagemOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        PontoReciclagem pontoReciclagem = pontoReciclagemOpt.get();
        pontoReciclagem.setTipoResiduos(pontoReciclagemRequestDTO.tipoResiduos());
        pontoReciclagem.setDescricao(pontoReciclagemRequestDTO.descricao());

        pontoReciclagemRepository.save(pontoReciclagem);

        return ResponseEntity.status(HttpStatus.OK).body(pontoReciclagem);
    }

    // DELETE - Remove um ponto de reciclagem por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePontoReciclagem(@PathVariable Integer id) {
        Optional<PontoReciclagem> pontoReciclagemOpt = pontoReciclagemRepository.findById(id);

        if (pontoReciclagemOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        pontoReciclagemRepository.delete(pontoReciclagemOpt.get());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
