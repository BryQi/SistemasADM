package com.municipio.sistemasadm.repository;

import com.municipio.sistemasadm.domain.FotoPozo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FotoPozo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FotoPozoRepository extends JpaRepository<FotoPozo, Long> {}
