package com.exa.unicen.arbolado.repository;

import com.exa.unicen.arbolado.domain.Calle;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Calle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CalleRepository extends JpaRepository<Calle, Long> {}
