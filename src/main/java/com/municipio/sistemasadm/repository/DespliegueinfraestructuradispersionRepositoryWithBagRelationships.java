package com.municipio.sistemasadm.repository;

import com.municipio.sistemasadm.domain.Despliegueinfraestructuradispersion;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface DespliegueinfraestructuradispersionRepositoryWithBagRelationships {
    Optional<Despliegueinfraestructuradispersion> fetchBagRelationships(
        Optional<Despliegueinfraestructuradispersion> despliegueinfraestructuradispersion
    );

    List<Despliegueinfraestructuradispersion> fetchBagRelationships(
        List<Despliegueinfraestructuradispersion> despliegueinfraestructuradispersions
    );

    Page<Despliegueinfraestructuradispersion> fetchBagRelationships(
        Page<Despliegueinfraestructuradispersion> despliegueinfraestructuradispersions
    );
}
