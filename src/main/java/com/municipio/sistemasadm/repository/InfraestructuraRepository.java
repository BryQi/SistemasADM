package com.municipio.sistemasadm.repository;

import com.municipio.sistemasadm.domain.Infraestructura;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Infraestructura entity.
 *
 * When extending this class, extend InfraestructuraRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface InfraestructuraRepository extends InfraestructuraRepositoryWithBagRelationships, JpaRepository<Infraestructura, Long> {
    default Optional<Infraestructura> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<Infraestructura> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<Infraestructura> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select distinct infraestructura from Infraestructura infraestructura left join fetch infraestructura.razonSocial",
        countQuery = "select count(distinct infraestructura) from Infraestructura infraestructura"
    )
    Page<Infraestructura> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct infraestructura from Infraestructura infraestructura left join fetch infraestructura.razonSocial")
    List<Infraestructura> findAllWithToOneRelationships();

    @Query(
        "select infraestructura from Infraestructura infraestructura left join fetch infraestructura.razonSocial where infraestructura.id =:id"
    )
    Optional<Infraestructura> findOneWithToOneRelationships(@Param("id") Long id);
}
