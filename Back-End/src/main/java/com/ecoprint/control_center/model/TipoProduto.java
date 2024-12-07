package com.ecoprint.control_center.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;

@Entity(name = "tipo_produto")
@Table(name = "tipo_produto")
@Getter
@Setter
@Builder  // Gera o método builder()
@AllArgsConstructor  // Cria um construtor com todos os campos
@NoArgsConstructor  // Cria um construtor sem argumentos
public class TipoProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;  //autoincremental

    @Column(nullable = false, unique = true)
    private String tipo;

}
