package com.municipio.sistemasadm.repository;

import com.municipio.sistemasadm.domain.RegistroInspecciones;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the RegistroInspecciones entity.
 */
@Repository
public interface RegistroInspeccionesRepository extends JpaRepository<RegistroInspecciones, Long> {
    default Optional<RegistroInspecciones> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<RegistroInspecciones> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<RegistroInspecciones> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct registroInspecciones from RegistroInspecciones registroInspecciones left join fetch registroInspecciones.razonSocial left join fetch registroInspecciones.numeropozo",
        countQuery = "select count(distinct registroInspecciones) from RegistroInspecciones registroInspecciones"
    )
    Page<RegistroInspecciones> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct registroInspecciones from RegistroInspecciones registroInspecciones left join fetch registroInspecciones.razonSocial left join fetch registroInspecciones.numeropozo"
    )
    List<RegistroInspecciones> findAllWithToOneRelationships();

    @Query(
        "select registroInspecciones from RegistroInspecciones registroInspecciones left join fetch registroInspecciones.razonSocial left join fetch registroInspecciones.numeropozo where registroInspecciones.id =:id"
    )
    Optional<RegistroInspecciones> findOneWithToOneRelationships(@Param("id") Long id);
}
