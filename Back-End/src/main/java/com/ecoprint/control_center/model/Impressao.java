// package com.ecoprint.control_center.model;

// import jakarta.persistence.*;
// import lombok.*;

// import java.util.List;

// @Entity(name = "impressao")
// @Table(name = "impressao")
// @Getter
// @Setter
// @Builder
// @AllArgsConstructor
// @NoArgsConstructor
// public class Impressao {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private int id;

//     @Column(nullable = false)
//     private Long custo;

//     @ElementCollection
//     @CollectionTable(name = "impressao_insumos_utilizados", joinColumns = @JoinColumn(name = "impressao_id"))
//     @Column(name = "insumo_id")
//     private List<Long> insumosUtilizados;
// }
