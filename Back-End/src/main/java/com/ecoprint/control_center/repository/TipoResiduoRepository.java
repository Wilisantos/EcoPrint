package com.ecoprint.control_center.repository;

import com.ecoprint.control_center.model.TipoResiduo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface TipoResiduoRepository extends JpaRepository<TipoResiduo, Long>{

    // Verifica se existe um tipo ou descrição já cadastrados
    @Query("SELECT COUNT(t) > 0 FROM tipo_residuo t WHERE t.tipo = :tipo OR t.descricao = :descricao")
    boolean existsByTipoOrDescricao(String tipo, String descricao);

}
