package com.ecoprint.control_center.model;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "insumo")
@Table(name = "insumo")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Insumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String tipoInsumo;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private Long quantidade;
}
