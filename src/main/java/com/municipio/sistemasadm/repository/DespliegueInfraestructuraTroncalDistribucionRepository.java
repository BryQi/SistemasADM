package com.municipio.sistemasadm.repository;

import com.municipio.sistemasadm.domain.DespliegueInfraestructuraTroncalDistribucion;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DespliegueInfraestructuraTroncalDistribucion entity.
 *
 * When extending this class, extend DespliegueInfraestructuraTroncalDistribucionRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface DespliegueInfraestructuraTroncalDistribucionRepository
    extends
        DespliegueInfraestructuraTroncalDistribucionRepositoryWithBagRelationships,
        JpaRepository<DespliegueInfraestructuraTroncalDistribucion, Long> {
    default Optional<DespliegueInfraestructuraTroncalDistribucion> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<DespliegueInfraestructuraTroncalDistribucion> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<DespliegueInfraestructuraTroncalDistribucion> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
