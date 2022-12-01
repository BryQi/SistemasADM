package com.municipio.sistemasadm.repository;

import com.municipio.sistemasadm.domain.DespliegueInfraestructuraTroncalDistribucion;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class DespliegueInfraestructuraTroncalDistribucionRepositoryWithBagRelationshipsImpl
    implements DespliegueInfraestructuraTroncalDistribucionRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<DespliegueInfraestructuraTroncalDistribucion> fetchBagRelationships(
        Optional<DespliegueInfraestructuraTroncalDistribucion> despliegueInfraestructuraTroncalDistribucion
    ) {
        return despliegueInfraestructuraTroncalDistribucion.map(this::fetchPozos);
    }

    @Override
    public Page<DespliegueInfraestructuraTroncalDistribucion> fetchBagRelationships(
        Page<DespliegueInfraestructuraTroncalDistribucion> despliegueInfraestructuraTroncalDistribucions
    ) {
        return new PageImpl<>(
            fetchBagRelationships(despliegueInfraestructuraTroncalDistribucions.getContent()),
            despliegueInfraestructuraTroncalDistribucions.getPageable(),
            despliegueInfraestructuraTroncalDistribucions.getTotalElements()
        );
    }

    @Override
    public List<DespliegueInfraestructuraTroncalDistribucion> fetchBagRelationships(
        List<DespliegueInfraestructuraTroncalDistribucion> despliegueInfraestructuraTroncalDistribucions
    ) {
        return Optional.of(despliegueInfraestructuraTroncalDistribucions).map(this::fetchPozos).orElse(Collections.emptyList());
    }

    DespliegueInfraestructuraTroncalDistribucion fetchPozos(DespliegueInfraestructuraTroncalDistribucion result) {
        return entityManager
            .createQuery(
                "select despliegueInfraestructuraTroncalDistribucion from DespliegueInfraestructuraTroncalDistribucion despliegueInfraestructuraTroncalDistribucion left join fetch despliegueInfraestructuraTroncalDistribucion.pozos where despliegueInfraestructuraTroncalDistribucion is :despliegueInfraestructuraTroncalDistribucion",
                DespliegueInfraestructuraTroncalDistribucion.class
            )
            .setParameter("despliegueInfraestructuraTroncalDistribucion", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<DespliegueInfraestructuraTroncalDistribucion> fetchPozos(
        List<DespliegueInfraestructuraTroncalDistribucion> despliegueInfraestructuraTroncalDistribucions
    ) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream
            .range(0, despliegueInfraestructuraTroncalDistribucions.size())
            .forEach(index -> order.put(despliegueInfraestructuraTroncalDistribucions.get(index).getId(), index));
        List<DespliegueInfraestructuraTroncalDistribucion> result = entityManager
            .createQuery(
                "select distinct despliegueInfraestructuraTroncalDistribucion from DespliegueInfraestructuraTroncalDistribucion despliegueInfraestructuraTroncalDistribucion left join fetch despliegueInfraestructuraTroncalDistribucion.pozos where despliegueInfraestructuraTroncalDistribucion in :despliegueInfraestructuraTroncalDistribucions",
                DespliegueInfraestructuraTroncalDistribucion.class
            )
            .setParameter("despliegueInfraestructuraTroncalDistribucions", despliegueInfraestructuraTroncalDistribucions)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
