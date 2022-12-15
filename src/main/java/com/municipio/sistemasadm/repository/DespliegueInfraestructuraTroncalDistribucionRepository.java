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
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<DespliegueInfraestructuraTroncalDistribucion> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<DespliegueInfraestructuraTroncalDistribucion> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select distinct despliegueInfraestructuraTroncalDistribucion from DespliegueInfraestructuraTroncalDistribucion despliegueInfraestructuraTroncalDistribucion left join fetch despliegueInfraestructuraTroncalDistribucion.razonSocial",
        countQuery = "select count(distinct despliegueInfraestructuraTroncalDistribucion) from DespliegueInfraestructuraTroncalDistribucion despliegueInfraestructuraTroncalDistribucion"
    )
    Page<DespliegueInfraestructuraTroncalDistribucion> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct despliegueInfraestructuraTroncalDistribucion from DespliegueInfraestructuraTroncalDistribucion despliegueInfraestructuraTroncalDistribucion left join fetch despliegueInfraestructuraTroncalDistribucion.razonSocial"
    )
    List<DespliegueInfraestructuraTroncalDistribucion> findAllWithToOneRelationships();

    @Query(
        "select despliegueInfraestructuraTroncalDistribucion from DespliegueInfraestructuraTroncalDistribucion despliegueInfraestructuraTroncalDistribucion left join fetch despliegueInfraestructuraTroncalDistribucion.razonSocial where despliegueInfraestructuraTroncalDistribucion.id =:id"
    )
    Optional<DespliegueInfraestructuraTroncalDistribucion> findOneWithToOneRelationships(@Param("id") Long id);
}
