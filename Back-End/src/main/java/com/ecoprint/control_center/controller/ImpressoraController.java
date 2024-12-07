package com.ecoprint.control_center.controller;

import com.ecoprint.control_center.controller.mapper.ImpressoraMapper;
import com.ecoprint.control_center.dto.ImpressoraRequestDTO;
import com.ecoprint.control_center.model.Impressora;
import com.ecoprint.control_center.repository.ImpressoraRepository;
import com.ecoprint.control_center.repository.TipoImpressoraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/impressoras")
@RequiredArgsConstructor
public class ImpressoraController {

    private final ImpressoraRepository impressoraRepository;

    private final TipoImpressoraRepository tipoImpressoraRepository;

    private final ImpressoraMapper impressoraMapper;

    // CREATE - Adiciona uma nova impressora
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Impressora create(@RequestBody ImpressoraRequestDTO impressoraRequestDTO) {

         tipoImpressoraRepository.findById(impressoraRequestDTO
                 .tipoImpressora()).orElseThrow(() -> new IllegalArgumentException("Não existe o tipoImpressao"));

        Impressora impressora = impressoraMapper.toModel(impressoraRequestDTO);
        return impressoraRepository.save(impressora);

    }

    // READ - Obtém todas as impressoras
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Impressora> getAll(Pageable pageable) {
        return impressoraRepository.findAll(pageable);
    }

    // READ por ID - Obtém uma impressora por ID
    @GetMapping("/{id}")
    public Impressora getImpressoraById(@PathVariable Integer id) {

        return impressoraRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Impressora nao existe"));

    }

    // UPDATE - Atualiza os dados de uma impressora
    @PutMapping("/{id}")
    public Impressora updateImpressora(@PathVariable Integer id, @RequestBody ImpressoraRequestDTO impressoraRequestDTO) {
        Impressora impressora = impressoraMapper.toModel(id, impressoraRequestDTO);
        return impressoraRepository.save(impressora);
    }

    // DELETE - Deleta uma impressora por ID
    @DeleteMapping("/{id}")
    public void deleteImpressora(@PathVariable Integer id) {
        impressoraRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Impressora nao existe"));
        impressoraRepository.deleteById(id);

    }
}
