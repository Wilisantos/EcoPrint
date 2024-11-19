package com.ecoprint.control_center.controller;

import com.ecoprint.control_center.dto.ResiduoRequestDTO;
import com.ecoprint.control_center.model.Residuo;
import com.ecoprint.control_center.model.TipoResiduo;
import com.ecoprint.control_center.repository.ResiduoRepository;
import com.ecoprint.control_center.repository.TipoResiduoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/residuos")
public class ResiduoController {

    private final ResiduoRepository residuoRepository;
    private final TipoResiduoRepository tipoResiduoRepository;

    public ResiduoController(ResiduoRepository residuoRepository, TipoResiduoRepository tipoResiduoRepository) {
        this.residuoRepository = residuoRepository;
        this.tipoResiduoRepository = tipoResiduoRepository;
    }

    // Criar um novo Residuo
    @PostMapping
    public ResponseEntity<String> createResiduo(@RequestBody ResiduoRequestDTO dto) {
        Optional<TipoResiduo> tipoResiduoOpt = tipoResiduoRepository.findById(dto.tipoResiduo());

        if (tipoResiduoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("TipoResiduo com ID " + dto.tipoResiduo() + " não encontrado.");
        }

        Residuo residuo = Residuo.builder()
                .tipoResiduo(tipoResiduoOpt.get())
                .quantidade(0) // Define quantidade inicial
                .build();

        residuoRepository.save(residuo);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Residuo criado com sucesso.");
    }

    @GetMapping
    public Page<Residuo> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return residuoRepository.findAll(PageRequest.of(page, size));
    }

    // Atualizar quantidade de um Residuo
    @PutMapping("/{id}/quantidade")
    public ResponseEntity<String> updateQuantidade(@PathVariable Long id, @RequestBody int quantidade) {
        Optional<Residuo> residuoOpt = residuoRepository.findById(id);

        if (residuoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Residuo com ID " + id + " não encontrado.");
        }

        Residuo residuo = residuoOpt.get();
        residuo.setQuantidade(quantidade);
        residuoRepository.save(residuo);

        return ResponseEntity.status(HttpStatus.OK)
                .body("Quantidade do residuo atualizada com sucesso.");
    }
}
