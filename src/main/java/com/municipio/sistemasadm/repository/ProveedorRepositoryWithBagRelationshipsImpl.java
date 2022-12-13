package com.municipio.sistemasadm.repository;

import com.municipio.sistemasadm.domain.Proveedor;
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
public class ProveedorRepositoryWithBagRelationshipsImpl implements ProveedorRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Proveedor> fetchBagRelationships(Optional<Proveedor> proveedor) {
        return proveedor.map(this::fetchUsers);
    }

    @Override
    public Page<Proveedor> fetchBagRelationships(Page<Proveedor> proveedors) {
        return new PageImpl<>(fetchBagRelationships(proveedors.getContent()), proveedors.getPageable(), proveedors.getTotalElements());
    }

    @Override
    public List<Proveedor> fetchBagRelationships(List<Proveedor> proveedors) {
        return Optional.of(proveedors).map(this::fetchUsers).orElse(Collections.emptyList());
    }

    Proveedor fetchUsers(Proveedor result) {
        return entityManager
            .createQuery(
                "select proveedor from Proveedor proveedor left join fetch proveedor.users where proveedor is :proveedor",
                Proveedor.class
            )
            .setParameter("proveedor", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Proveedor> fetchUsers(List<Proveedor> proveedors) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, proveedors.size()).forEach(index -> order.put(proveedors.get(index).getId(), index));
        List<Proveedor> result = entityManager
            .createQuery(
                "select distinct proveedor from Proveedor proveedor left join fetch proveedor.users where proveedor in :proveedors",
                Proveedor.class
            )
            .setParameter("proveedors", proveedors)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
