package com.municipio.sistemasadm.repository;

import com.municipio.sistemasadm.domain.Despliegueinfraestructuradispersion;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Despliegueinfraestructuradispersion entity.
 *
 * When extending this class, extend DespliegueinfraestructuradispersionRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface DespliegueinfraestructuradispersionRepository
    extends DespliegueinfraestructuradispersionRepositoryWithBagRelationships, JpaRepository<Despliegueinfraestructuradispersion, Long> {
    default Optional<Despliegueinfraestructuradispersion> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<Despliegueinfraestructuradispersion> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<Despliegueinfraestructuradispersion> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select distinct despliegueinfraestructuradispersion from Despliegueinfraestructuradispersion despliegueinfraestructuradispersion left join fetch despliegueinfraestructuradispersion.nombreRuta left join fetch despliegueinfraestructuradispersion.razonSocial",
        countQuery = "select count(distinct despliegueinfraestructuradispersion) from Despliegueinfraestructuradispersion despliegueinfraestructuradispersion"
    )
    Page<Despliegueinfraestructuradispersion> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct despliegueinfraestructuradispersion from Despliegueinfraestructuradispersion despliegueinfraestructuradispersion left join fetch despliegueinfraestructuradispersion.nombreRuta left join fetch despliegueinfraestructuradispersion.razonSocial"
    )
    List<Despliegueinfraestructuradispersion> findAllWithToOneRelationships();

    @Query(
        "select despliegueinfraestructuradispersion from Despliegueinfraestructuradispersion despliegueinfraestructuradispersion left join fetch despliegueinfraestructuradispersion.nombreRuta left join fetch despliegueinfraestructuradispersion.razonSocial where despliegueinfraestructuradispersion.id =:id"
    )
    Optional<Despliegueinfraestructuradispersion> findOneWithToOneRelationships(@Param("id") Long id);
}
