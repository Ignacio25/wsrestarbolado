package com.exa.unicen.arbolado.repository;

import com.exa.unicen.arbolado.domain.Arbol;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface ArbolRepository extends JpaRepository<Arbol, Long> {
	
	List<Arbol> findAllByEspecieId(Long especieId);

	@Query(value = "SELECT a FROM Arbol a ORDER BY updatedAt DESC LIMIT 1")
	Arbol findOneByLastUpdatedAt();
	
	Page<Arbol> findAllByOrderByUpdatedAtDesc(Pageable pageable); 
}