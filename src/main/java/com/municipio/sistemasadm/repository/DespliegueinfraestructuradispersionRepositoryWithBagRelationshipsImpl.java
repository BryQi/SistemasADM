package com.municipio.sistemasadm.repository;

import com.municipio.sistemasadm.domain.Despliegueinfraestructuradispersion;
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
public class DespliegueinfraestructuradispersionRepositoryWithBagRelationshipsImpl
    implements DespliegueinfraestructuradispersionRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Despliegueinfraestructuradispersion> fetchBagRelationships(
        Optional<Despliegueinfraestructuradispersion> despliegueinfraestructuradispersion
    ) {
        return despliegueinfraestructuradispersion.map(this::fetchNumeropozos);
    }

    @Override
    public Page<Despliegueinfraestructuradispersion> fetchBagRelationships(
        Page<Despliegueinfraestructuradispersion> despliegueinfraestructuradispersions
    ) {
        return new PageImpl<>(
            fetchBagRelationships(despliegueinfraestructuradispersions.getContent()),
            despliegueinfraestructuradispersions.getPageable(),
            despliegueinfraestructuradispersions.getTotalElements()
        );
    }

    @Override
    public List<Despliegueinfraestructuradispersion> fetchBagRelationships(
        List<Despliegueinfraestructuradispersion> despliegueinfraestructuradispersions
    ) {
        return Optional.of(despliegueinfraestructuradispersions).map(this::fetchNumeropozos).orElse(Collections.emptyList());
    }

    Despliegueinfraestructuradispersion fetchNumeropozos(Despliegueinfraestructuradispersion result) {
        return entityManager
            .createQuery(
                "select despliegueinfraestructuradispersion from Despliegueinfraestructuradispersion despliegueinfraestructuradispersion left join fetch despliegueinfraestructuradispersion.numeropozos where despliegueinfraestructuradispersion is :despliegueinfraestructuradispersion",
                Despliegueinfraestructuradispersion.class
            )
            .setParameter("despliegueinfraestructuradispersion", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Despliegueinfraestructuradispersion> fetchNumeropozos(
        List<Despliegueinfraestructuradispersion> despliegueinfraestructuradispersions
    ) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream
            .range(0, despliegueinfraestructuradispersions.size())
            .forEach(index -> order.put(despliegueinfraestructuradispersions.get(index).getId(), index));
        List<Despliegueinfraestructuradispersion> result = entityManager
            .createQuery(
                "select distinct despliegueinfraestructuradispersion from Despliegueinfraestructuradispersion despliegueinfraestructuradispersion left join fetch despliegueinfraestructuradispersion.numeropozos where despliegueinfraestructuradispersion in :despliegueinfraestructuradispersions",
                Despliegueinfraestructuradispersion.class
            )
            .setParameter("despliegueinfraestructuradispersions", despliegueinfraestructuradispersions)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
