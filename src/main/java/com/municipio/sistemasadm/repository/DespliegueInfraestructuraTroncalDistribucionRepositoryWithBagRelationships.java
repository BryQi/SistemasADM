package com.municipio.sistemasadm.repository;

import com.municipio.sistemasadm.domain.DespliegueInfraestructuraTroncalDistribucion;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface DespliegueInfraestructuraTroncalDistribucionRepositoryWithBagRelationships {
    Optional<DespliegueInfraestructuraTroncalDistribucion> fetchBagRelationships(
        Optional<DespliegueInfraestructuraTroncalDistribucion> despliegueInfraestructuraTroncalDistribucion
    );

    List<DespliegueInfraestructuraTroncalDistribucion> fetchBagRelationships(
        List<DespliegueInfraestructuraTroncalDistribucion> despliegueInfraestructuraTroncalDistribucions
    );

    Page<DespliegueInfraestructuraTroncalDistribucion> fetchBagRelationships(
        Page<DespliegueInfraestructuraTroncalDistribucion> despliegueInfraestructuraTroncalDistribucions
    );
}
