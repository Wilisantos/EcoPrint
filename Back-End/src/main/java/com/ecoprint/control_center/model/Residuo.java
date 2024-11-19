package com.ecoprint.control_center.model;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "residuo")
@Table(name = "residuo")
@Getter
@Setter
@Builder  // Gera o método builder()
@AllArgsConstructor  // Cria um construtor com todos os campos
@NoArgsConstructor  // Cria um construtor sem argumentos
public class Residuo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // autoincremental

    @ManyToOne
    @JoinColumn(name = "tipo_residuo_id", nullable = false)
    private TipoResiduo tipoResiduo;

    @Column(nullable = false)
    private int quantidade;

}