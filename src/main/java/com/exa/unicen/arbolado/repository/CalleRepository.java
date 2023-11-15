package com.exa.unicen.arbolado.repository;

import com.exa.unicen.arbolado.domain.Calle;
import org.springframework.data.jpa.repository.*;

@SuppressWarnings("unused")
public interface CalleRepository extends JpaRepository<Calle, Long> {}
