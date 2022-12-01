package com.municipio.sistemasadm.repository;

import com.municipio.sistemasadm.domain.Autorizaciones;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Autorizaciones entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AutorizacionesRepository extends JpaRepository<Autorizaciones, Long> {}
