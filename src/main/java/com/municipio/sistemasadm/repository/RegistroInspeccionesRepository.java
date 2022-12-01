package com.municipio.sistemasadm.repository;

import com.municipio.sistemasadm.domain.RegistroInspecciones;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RegistroInspecciones entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegistroInspeccionesRepository extends JpaRepository<RegistroInspecciones, Long> {}
