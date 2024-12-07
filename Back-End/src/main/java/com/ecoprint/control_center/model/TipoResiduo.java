package com.ecoprint.control_center.model;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "tipo_residuo")
@Table(name = "tipo_residuo")
@Getter
@Setter
@Builder  // Gera o método builder()
@AllArgsConstructor  // Cria um construtor com todos os campos
@NoArgsConstructor  // Cria um construtor sem argumentos
public class TipoResiduo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;  //autoincremental

    @Column(nullable = false, unique = true)
    private String tipo;

    @Column(nullable = false, unique = true)
    private String descricao;


}
