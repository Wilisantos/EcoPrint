package com.ecoprint.control_center.controller;

import com.ecoprint.control_center.dto.TipoImpressoraRequestDTO;
import com.ecoprint.control_center.model.TipoImpressora;
import com.ecoprint.control_center.repository.ImpressoraRepository;
import com.ecoprint.control_center.repository.TipoImpressoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/tipos-impressora")
public class TipoImpressoraController {

    @Autowired
    private TipoImpressoraRepository tipoImpressoraRepository;

    @Autowired
    private ImpressoraRepository impressoraRepository;

    // CREATE - Adiciona um novo tipo de impressora
    @PostMapping
    public ResponseEntity<TipoImpressora> create(@RequestBody TipoImpressoraRequestDTO tipoImpressoraRequestDTO) {
        TipoImpressora tipoImpressora = TipoImpressora.builder()
                .tipo(tipoImpressoraRequestDTO.tipo())
                .build();

        tipoImpressoraRepository.save(tipoImpressora);

        return ResponseEntity.status(HttpStatus.CREATED).body(tipoImpressora);
    }

    // READ - Obtém todos os tipos de impressora
    @GetMapping
    public ResponseEntity<Page<TipoImpressora>> getAllTiposImpressora(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<TipoImpressora> tiposImpressora = tipoImpressoraRepository.findAll(PageRequest.of(page, size));
        return ResponseEntity.status(HttpStatus.OK).body(tiposImpressora);
    }

    // READ - Obtém um tipo de impressora por ID
    @GetMapping("/{id}")
    public ResponseEntity<TipoImpressora> getTipoImpressoraById(@PathVariable Integer id) {
        Optional<TipoImpressora> tipoImpressoraOpt = tipoImpressoraRepository.findById(id);

        if (!tipoImpressoraOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(tipoImpressoraOpt.get());
    }

    // UPDATE - Atualiza um tipo de impressora
    @PutMapping("/{id}")
    public ResponseEntity<TipoImpressora> updateTipoImpressora(@PathVariable Integer id, @RequestBody TipoImpressoraRequestDTO tipoImpressoraRequestDTO) {
        Optional<TipoImpressora> tipoImpressoraOpt = tipoImpressoraRepository.findById(id);

        if (!tipoImpressoraOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        TipoImpressora tipoImpressora = tipoImpressoraOpt.get();
        tipoImpressora.setTipo(tipoImpressoraRequestDTO.tipo());

        tipoImpressoraRepository.save(tipoImpressora);

        return ResponseEntity.status(HttpStatus.OK).body(tipoImpressora);
    }

    // DELETE - Deleta um tipo de impressora por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTipoImpressora(@PathVariable Integer id) {
        Optional<TipoImpressora> tipoImpressoraOpt = tipoImpressoraRepository.findById(id);

        if (!tipoImpressoraOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Verifica se o tipo de impressora está vinculado a alguma impressora
        long count = impressoraRepository.countByTipoImpressoraId(id);
        if (count > 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(null); // Retorna 400 se o tipo de impressora estiver vinculado
        }

        tipoImpressoraRepository.delete(tipoImpressoraOpt.get());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
