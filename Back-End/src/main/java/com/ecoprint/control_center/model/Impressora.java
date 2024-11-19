package com.ecoprint.control_center.model;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "impressora")
@Table(name = "impressora")
@Getter
@Setter
@Builder  // Gera o método builder()
@AllArgsConstructor  // Cria um construtor com todos os campos
@NoArgsConstructor  // Cria um construtor sem argumentos
public class Impressora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // autoincremental

    @ManyToOne
    @JoinColumn(name = "tipo_impressora_id", nullable = false)
    private TipoImpressora tipoImpressora;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false)
    private Long capacidade;
}