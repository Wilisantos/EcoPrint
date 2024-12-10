// package com.ecoprint.control_center.controller;

// import com.ecoprint.control_center.dto.ImpressaoRequestDTO;
// import com.ecoprint.control_center.model.Impressao;
// import com.ecoprint.control_center.repository.ImpressaoRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.Optional;

// @RestController
// @RequestMapping("/impressoes")
// public class ImpressaoController {

//     @Autowired
//     private ImpressaoRepository impressaoRepository;

//     // CREATE - Adiciona uma nova impressão
//     @PostMapping
//     public ResponseEntity<Impressao> create(@RequestBody ImpressaoRequestDTO impressaoRequestDTO) {
//         Impressao impressao = Impressao.builder()
//                 .custo(impressaoRequestDTO.custo())
//                 .insumosUtilizados(impressaoRequestDTO.insumosUtilizados())
//                 .build();

//         impressaoRepository.save(impressao);
//         return ResponseEntity.status(HttpStatus.CREATED).body(impressao);
//     }

//     // READ - Obtém todas as impressões
//     @GetMapping
//     public Page<Impressao> getAll(
//             @RequestParam(defaultValue = "0") int page,
//             @RequestParam(defaultValue = "10") int size) {
//         return impressaoRepository.findAll(PageRequest.of(page, size));
//     }

//     // READ - Obtém uma impressão por ID
//     @GetMapping("/{id}")
//     public ResponseEntity<Impressao> getById(@PathVariable Integer id) {
//         Optional<Impressao> impressaoOpt = impressaoRepository.findById(id);

//         if (impressaoOpt.isEmpty()) {
//             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//         }

//         return ResponseEntity.status(HttpStatus.OK).body(impressaoOpt.get());
//     }

//     // UPDATE - Atualiza uma impressão existente
//     @PutMapping("/{id}")
//     public ResponseEntity<Impressao> update(@PathVariable Integer id, @RequestBody ImpressaoRequestDTO impressaoRequestDTO) {
//         Optional<Impressao> impressaoOpt = impressaoRepository.findById(id);

//         if (impressaoOpt.isEmpty()) {
//             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//         }

//         Impressao impressao = impressaoOpt.get();
//         impressao.setCusto(impressaoRequestDTO.custo());
//         impressao.setInsumosUtilizados(impressaoRequestDTO.insumosUtilizados());

//         impressaoRepository.save(impressao);
//         return ResponseEntity.status(HttpStatus.OK).body(impressao);
//     }

//     // DELETE - Remove uma impressão por ID
//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> delete(@PathVariable Integer id) {
//         Optional<Impressao> impressaoOpt = impressaoRepository.findById(id);

//         if (impressaoOpt.isEmpty()) {
//             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//         }

//         impressaoRepository.delete(impressaoOpt.get());
//         return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
//     }
// }
