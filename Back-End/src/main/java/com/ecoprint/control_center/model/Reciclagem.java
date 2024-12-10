package com.ecoprint.control_center.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "reciclagem")
@Table(name = "reciclagem")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reciclagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ElementCollection
    @CollectionTable(name = "reciclagem_tipo_residuos", joinColumns = @JoinColumn(name = "reciclagem_id"))
    @Column(name = "tipo_residuo_id")
    private List<Long> tipoResiduos;

    @Column(nullable = false)
    private String pontoReciclagem;

    @Column(nullable = false)
    private Long valorGanho;
}
