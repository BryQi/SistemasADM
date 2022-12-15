package com.municipio.sistemasadm.repository;

import com.municipio.sistemasadm.domain.Autorizaciones;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Autorizaciones entity.
 */
@Repository
public interface AutorizacionesRepository extends JpaRepository<Autorizaciones, Long> {
    default Optional<Autorizaciones> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Autorizaciones> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Autorizaciones> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct autorizaciones from Autorizaciones autorizaciones left join fetch autorizaciones.razonSocial left join fetch autorizaciones.numeropozo",
        countQuery = "select count(distinct autorizaciones) from Autorizaciones autorizaciones"
    )
    Page<Autorizaciones> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct autorizaciones from Autorizaciones autorizaciones left join fetch autorizaciones.razonSocial left join fetch autorizaciones.numeropozo"
    )
    List<Autorizaciones> findAllWithToOneRelationships();

    @Query(
        "select autorizaciones from Autorizaciones autorizaciones left join fetch autorizaciones.razonSocial left join fetch autorizaciones.numeropozo where autorizaciones.id =:id"
    )
    Optional<Autorizaciones> findOneWithToOneRelationships(@Param("id") Long id);
}
