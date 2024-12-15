package com.ecoprint.control_center.repository;

import com.ecoprint.control_center.model.TipoProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface TipoProdutoRepository extends JpaRepository<TipoProduto, Integer>{

}
