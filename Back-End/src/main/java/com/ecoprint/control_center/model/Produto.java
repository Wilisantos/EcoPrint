package com.ecoprint.control_center.model;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "produto")
@Table(name = "produto")
@Getter
@Setter
@Builder  // Gera o método builder()
@AllArgsConstructor  // Cria um construtor com todos os campos
@NoArgsConstructor  // Cria um construtor sem argumentos
public class produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // autoincremental

    @ManyToOne
    @JoinColumn(name = "tipo_produto_id", nullable = false)
    private Long tipoProduto;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private Long valor;
}