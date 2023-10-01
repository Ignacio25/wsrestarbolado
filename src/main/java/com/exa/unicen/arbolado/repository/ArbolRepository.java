package com.exa.unicen.arbolado.repository;

import com.exa.unicen.arbolado.domain.Arbol;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Arbol entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArbolRepository extends JpaRepository<Arbol, Long> {}
