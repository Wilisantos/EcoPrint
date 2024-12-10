package com.ecoprint.control_center.controller;

import com.ecoprint.control_center.dto.TipoResiduoRequestDTO;
import com.ecoprint.control_center.model.TipoResiduo;
import com.ecoprint.control_center.repository.TipoResiduoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tipos-residuo")
public class TipoResiduoController {

    @Autowired
    private TipoResiduoRepository tipoResiduoRepository;

    // CREATE - Adiciona um novo tipo de resíduo
    @PostMapping
    public ResponseEntity<TipoResiduo> create(@RequestBody TipoResiduoRequestDTO tipoResiduoRequestDTO) {
        // Verifica se já existe um tipo com o mesmo nome ou descrição
        if (tipoResiduoRepository.existsByTipoOrDescricao(tipoResiduoRequestDTO.tipo(), tipoResiduoRequestDTO.descricao())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        TipoResiduo tipoResiduo = TipoResiduo.builder()
                .tipo(tipoResiduoRequestDTO.tipo())
                .descricao(tipoResiduoRequestDTO.descricao())
                .build();

        TipoResiduo saved = tipoResiduoRepository.save(tipoResiduo);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // READ - Lista todos os tipos de resíduos
    @GetMapping
    public ResponseEntity<List<TipoResiduo>> findAll() {
        List<TipoResiduo> tiposResiduos = tipoResiduoRepository.findAll();
        return ResponseEntity.ok(tiposResiduos);
    }

    // READ - Busca um tipo de resíduo pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<TipoResiduo> findById(@PathVariable Integer id) {
        Optional<TipoResiduo> tipoResiduoOpt = tipoResiduoRepository.findById(id);
        return tipoResiduoOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // UPDATE - Atualiza um tipo de resíduo existente
    @PutMapping("/{id}")
    public ResponseEntity<TipoResiduo> update(@PathVariable Integer id, @RequestBody TipoResiduoRequestDTO tipoResiduoRequestDTO) {
        Optional<TipoResiduo> tipoResiduoOpt = tipoResiduoRepository.findById(id);

        if (tipoResiduoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Atualiza os valores
        TipoResiduo tipoResiduo = tipoResiduoOpt.get();
        tipoResiduo.setTipo(tipoResiduoRequestDTO.tipo());
        tipoResiduo.setDescricao(tipoResiduoRequestDTO.descricao());

        TipoResiduo updated = tipoResiduoRepository.save(tipoResiduo);
        return ResponseEntity.ok(updated);
    }

    // DELETE - Remove um tipo de resíduo pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!tipoResiduoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        tipoResiduoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
