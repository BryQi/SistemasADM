package com.municipio.sistemasadm.repository;

import com.municipio.sistemasadm.domain.Pozo;
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
public class PozoRepositoryWithBagRelationshipsImpl implements PozoRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Pozo> fetchBagRelationships(Optional<Pozo> pozo) {
        return pozo.map(this::fetchInfraestructuras);
    }

    @Override
    public Page<Pozo> fetchBagRelationships(Page<Pozo> pozos) {
        return new PageImpl<>(fetchBagRelationships(pozos.getContent()), pozos.getPageable(), pozos.getTotalElements());
    }

    @Override
    public List<Pozo> fetchBagRelationships(List<Pozo> pozos) {
        return Optional.of(pozos).map(this::fetchInfraestructuras).orElse(Collections.emptyList());
    }

    Pozo fetchInfraestructuras(Pozo result) {
        return entityManager
            .createQuery("select pozo from Pozo pozo left join fetch pozo.infraestructuras where pozo is :pozo", Pozo.class)
            .setParameter("pozo", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Pozo> fetchInfraestructuras(List<Pozo> pozos) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, pozos.size()).forEach(index -> order.put(pozos.get(index).getId(), index));
        List<Pozo> result = entityManager
            .createQuery("select distinct pozo from Pozo pozo left join fetch pozo.infraestructuras where pozo in :pozos", Pozo.class)
            .setParameter("pozos", pozos)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
