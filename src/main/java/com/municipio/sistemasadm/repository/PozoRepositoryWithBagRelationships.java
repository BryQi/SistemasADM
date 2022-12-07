package com.municipio.sistemasadm.repository;

import com.municipio.sistemasadm.domain.Pozo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface PozoRepositoryWithBagRelationships {
    Optional<Pozo> fetchBagRelationships(Optional<Pozo> pozo);

    List<Pozo> fetchBagRelationships(List<Pozo> pozos);

    Page<Pozo> fetchBagRelationships(Page<Pozo> pozos);
}
