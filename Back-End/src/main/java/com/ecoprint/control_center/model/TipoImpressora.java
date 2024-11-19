package com.ecoprint.control_center.model;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "tipo_impressora")
@Table(name = "tipo_impressora")
@Getter
@Setter
@Builder  // Gera o método builder()
@AllArgsConstructor  // Cria um construtor com todos os campos
@NoArgsConstructor  // Cria um construtor sem argumentos
public class TipoImpressora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  //autoincremental

    @Column(nullable = false, unique = true)
    private String tipo;
}
