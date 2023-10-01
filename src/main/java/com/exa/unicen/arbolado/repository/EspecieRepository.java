package com.exa.unicen.arbolado.repository;

import com.exa.unicen.arbolado.domain.Especie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Especie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EspecieRepository extends JpaRepository<Especie, Long> {}
