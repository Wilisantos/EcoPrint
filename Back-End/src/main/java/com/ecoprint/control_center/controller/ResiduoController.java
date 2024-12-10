package com.ecoprint.control_center.controller;

import com.ecoprint.control_center.controller.mapper.ResiduoMapper;
import com.ecoprint.control_center.dto.ResiduoRequestDTO;
import com.ecoprint.control_center.model.Residuo;
import com.ecoprint.control_center.repository.ResiduoRepository;
import com.ecoprint.control_center.repository.TipoResiduoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/residuos")
@RequiredArgsConstructor
public class ResiduoController {

    private final ResiduoRepository residuoRepository;
    private final TipoResiduoRepository tipoResiduoRepository;
    private final ResiduoMapper residuoMapper;

    // CREATE - Adiciona um novo resíduo
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Residuo create(@RequestBody ResiduoRequestDTO residuoRequestDTO) {

        tipoResiduoRepository.findById(residuoRequestDTO.tipoResiduo())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de resíduo não encontrado"));

        Residuo residuo = residuoMapper.toModel(residuoRequestDTO);
        return residuoRepository.save(residuo);
    }

    // READ - Obtém todos os resíduos
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Residuo> getAll(Pageable pageable) {
        return residuoRepository.findAll(pageable);
    }

    // READ por ID - Obtém um resíduo por ID
    @GetMapping("/{id}")
    public Residuo getResiduoById(@PathVariable Integer id) {
        return residuoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Resíduo não encontrado"));
    }

    // UPDATE - Atualiza os dados de um resíduo
    @PutMapping("/{id}")
    public Residuo updateResiduo(@PathVariable Integer id, @RequestBody ResiduoRequestDTO residuoRequestDTO) {
        Residuo residuo = residuoMapper.toModel(id, residuoRequestDTO);
        return residuoRepository.save(residuo);
    }

    // DELETE - Deleta um resíduo por ID
    @DeleteMapping("/{id}")
    public void deleteResiduo(@PathVariable Integer id) {
        residuoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Resíduo não encontrado"));
        residuoRepository.deleteById(id);
    }
}
