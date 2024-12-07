package com.ecoprint.control_center.repository;

import com.ecoprint.control_center.model.Reciclagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReciclagemRepository extends JpaRepository<Reciclagem, Integer> {}
