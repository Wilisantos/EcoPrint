package com.ecoprint.control_center.controller;

import com.ecoprint.control_center.dto.ImpressoraRequestDTO;
import com.ecoprint.control_center.model.Impressora;
import com.ecoprint.control_center.model.TipoImpressora;
import com.ecoprint.control_center.repository.ImpresoraRepository;
import com.ecoprint.control_center.repository.TipoImpressoraRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/impressoras")
public class ImpressoraController {

    @Autowired
    private ImpresoraRepository impressoraRepository;

    @Autowired
    private TipoImpressoraRepository tipoImpressoraRepository;

    // CREATE - Adiciona uma nova impressora
    @PostMapping
    public ResponseEntity<Impressora> create(@RequestBody ImpressoraRequestDTO impressoraRequestDTO) {
        // Buscar o tipo de impressora baseado no ID do DTO
        Optional<TipoImpressora> tipoImpressoraOpt = tipoImpressoraRepository.findById(impressoraRequestDTO.tipoImpressora());

        if (!tipoImpressoraOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }

        TipoImpressora tipoImpressora = tipoImpressoraOpt.get();

        Impressora impressora = Impressora.builder()
                .tipoImpressora(tipoImpressora)
                .modelo(impressoraRequestDTO.modelo())
                .capacidade(impressoraRequestDTO.capacidade())
                .build();

        impressoraRepository.save(impressora);

        return ResponseEntity.status(HttpStatus.CREATED).body(impressora);
    }

    // READ - Obtém todas as impressoras
    @GetMapping
    public Page<Impressora> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return impressoraRepository.findAll(PageRequest.of(page, size));
    }

    // READ - Obtém uma impressora por ID
    @GetMapping("/{id}")
    public ResponseEntity<Impressora> getImpressoraById(@PathVariable Long id) {
        Optional<Impressora> impressoraOpt = impressoraRepository.findById(id);

        if (!impressoraOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(impressoraOpt.get());
    }

    // UPDATE - Atualiza os dados de uma impressora
    @PutMapping("/{id}")
    public ResponseEntity<Impressora> updateImpressora(@PathVariable Long id, @RequestBody ImpressoraRequestDTO impressoraRequestDTO) {
        Optional<Impressora> impressoraOpt = impressoraRepository.findById(id);

        if (!impressoraOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Buscar o tipo de impressora baseado no ID do DTO
        Optional<TipoImpressora> tipoImpressoraOpt = tipoImpressoraRepository.findById(impressoraRequestDTO.tipoImpressora());

        if (!tipoImpressoraOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }

        TipoImpressora tipoImpressora = tipoImpressoraOpt.get();
        Impressora impressora = impressoraOpt.get();

        // Atualizando os dados da impressora
        impressora.setTipoImpressora(tipoImpressora);
        impressora.setModelo(impressoraRequestDTO.modelo());
        impressora.setCapacidade(impressoraRequestDTO.capacidade());

        impressoraRepository.save(impressora);

        return ResponseEntity.status(HttpStatus.OK).body(impressora);
    }

    // DELETE - Deleta uma impressora por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImpressora(@PathVariable Long id) {
        Optional<Impressora> impressoraOpt = impressoraRepository.findById(id);

        if (!impressoraOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        impressoraRepository.delete(impressoraOpt.get());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
