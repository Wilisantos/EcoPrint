package com.ecoprint.control_center.model;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "impressao")
@Table(name = "impressao")
@Getter
@Setter
@Builder  // Gera o método builder()
@AllArgsConstructor  // Cria um construtor com todos os campos
@NoArgsConstructor  // Cria um construtor sem argumentos
public class Impressao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;  // autoincremental

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = true, name = "iccmodel")
    private String iccModel;

    @Lob  // Usado para armazenar grandes dados, como imagens ou arquivos binários
    @Column(nullable = true, name = "dadosimagem", columnDefinition = "bytea")
    private byte[] dadosImagem;  // Campo para armazenar a imagem em Base64

    @Column(name = "cmykpredictor")
    private String cmykPredictor;


}