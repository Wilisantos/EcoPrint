package com.ecoprint.control_center.repository;

import com.ecoprint.control_center.model.PontoReciclagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PontoReciclagemRepository extends JpaRepository<PontoReciclagem, Integer> {}
