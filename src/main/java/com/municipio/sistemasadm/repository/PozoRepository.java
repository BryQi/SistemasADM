package com.municipio.sistemasadm.repository;

import com.municipio.sistemasadm.domain.Pozo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Pozo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PozoRepository extends JpaRepository<Pozo, Long> {}
