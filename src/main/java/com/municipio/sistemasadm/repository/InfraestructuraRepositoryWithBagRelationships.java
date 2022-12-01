package com.municipio.sistemasadm.repository;

import com.municipio.sistemasadm.domain.Infraestructura;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface InfraestructuraRepositoryWithBagRelationships {
    Optional<Infraestructura> fetchBagRelationships(Optional<Infraestructura> infraestructura);

    List<Infraestructura> fetchBagRelationships(List<Infraestructura> infraestructuras);

    Page<Infraestructura> fetchBagRelationships(Page<Infraestructura> infraestructuras);
}
