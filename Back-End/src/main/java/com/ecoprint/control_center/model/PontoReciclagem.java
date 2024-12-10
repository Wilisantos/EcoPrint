package com.ecoprint.control_center.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "ponto_reciclagem")
@Table(name = "ponto_reciclagem")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PontoReciclagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ElementCollection
    @CollectionTable(name = "ponto_reciclagem_tipo_residuos", joinColumns = @JoinColumn(name = "ponto_reciclagem_id"))
    @Column(name = "tipo_residuo_id")
    private List<Long> tipoResiduos;

    @Column(nullable = false)
    private String descricao;
}
