package com.municipio.sistemasadm.repository;

import com.municipio.sistemasadm.domain.FotoPozo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FotoPozo entity.
 */
@Repository
public interface FotoPozoRepository extends JpaRepository<FotoPozo, Long> {
    default Optional<FotoPozo> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<FotoPozo> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<FotoPozo> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct fotoPozo from FotoPozo fotoPozo left join fetch fotoPozo.numeropozo",
        countQuery = "select count(distinct fotoPozo) from FotoPozo fotoPozo"
    )
    Page<FotoPozo> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct fotoPozo from FotoPozo fotoPozo left join fetch fotoPozo.numeropozo")
    List<FotoPozo> findAllWithToOneRelationships();

    @Query("select fotoPozo from FotoPozo fotoPozo left join fetch fotoPozo.numeropozo where fotoPozo.id =:id")
    Optional<FotoPozo> findOneWithToOneRelationships(@Param("id") Long id);
}
