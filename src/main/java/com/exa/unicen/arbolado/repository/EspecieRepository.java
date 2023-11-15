package com.exa.unicen.arbolado.repository;

import com.exa.unicen.arbolado.domain.Especie;
import org.springframework.data.jpa.repository.*;

public interface EspecieRepository extends JpaRepository<Especie, Long> {}
