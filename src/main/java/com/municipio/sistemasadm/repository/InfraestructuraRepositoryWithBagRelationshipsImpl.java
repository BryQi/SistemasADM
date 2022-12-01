package com.municipio.sistemasadm.repository;

import com.municipio.sistemasadm.domain.Infraestructura;
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
public class InfraestructuraRepositoryWithBagRelationshipsImpl implements InfraestructuraRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Infraestructura> fetchBagRelationships(Optional<Infraestructura> infraestructura) {
        return infraestructura.map(this::fetchPozos);
    }

    @Override
    public Page<Infraestructura> fetchBagRelationships(Page<Infraestructura> infraestructuras) {
        return new PageImpl<>(
            fetchBagRelationships(infraestructuras.getContent()),
            infraestructuras.getPageable(),
            infraestructuras.getTotalElements()
        );
    }

    @Override
    public List<Infraestructura> fetchBagRelationships(List<Infraestructura> infraestructuras) {
        return Optional.of(infraestructuras).map(this::fetchPozos).orElse(Collections.emptyList());
    }

    Infraestructura fetchPozos(Infraestructura result) {
        return entityManager
            .createQuery(
                "select infraestructura from Infraestructura infraestructura left join fetch infraestructura.pozos where infraestructura is :infraestructura",
                Infraestructura.class
            )
            .setParameter("infraestructura", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Infraestructura> fetchPozos(List<Infraestructura> infraestructuras) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, infraestructuras.size()).forEach(index -> order.put(infraestructuras.get(index).getId(), index));
        List<Infraestructura> result = entityManager
            .createQuery(
                "select distinct infraestructura from Infraestructura infraestructura left join fetch infraestructura.pozos where infraestructura in :infraestructuras",
                Infraestructura.class
            )
            .setParameter("infraestructuras", infraestructuras)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
