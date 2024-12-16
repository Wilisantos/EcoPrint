package com.ecoprint.control_center.repository;

import com.ecoprint.control_center.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProdutoRepository extends JpaRepository<Produto, Integer>{

    // Usando @Query para buscar o iccModel a partir do TipoProduto relacionado
    @Query(value = """
            SELECT
            	iccmodel
            FROM
                produto prod
            INNER JOIN
                tipo_produto tp ON prod.tipo_produto_id = tp.id
            WHERE tp.id= :id
            """,nativeQuery = true)
    String findIccModelById(Integer id);  // Retorna o iccModel como uma string

}
