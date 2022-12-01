package com.municipio.sistemasadm.service;

import com.municipio.sistemasadm.service.dto.DespliegueInfraestructuraTroncalDistribucionDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.municipio.sistemasadm.domain.DespliegueInfraestructuraTroncalDistribucion}.
 */
public interface DespliegueInfraestructuraTroncalDistribucionService {
    /**
     * Save a despliegueInfraestructuraTroncalDistribucion.
     *
     * @param despliegueInfraestructuraTroncalDistribucionDTO the entity to save.
     * @return the persisted entity.
     */
    DespliegueInfraestructuraTroncalDistribucionDTO save(
        DespliegueInfraestructuraTroncalDistribucionDTO despliegueInfraestructuraTroncalDistribucionDTO
    );

    /**
     * Updates a despliegueInfraestructuraTroncalDistribucion.
     *
     * @param despliegueInfraestructuraTroncalDistribucionDTO the entity to update.
     * @return the persisted entity.
     */
    DespliegueInfraestructuraTroncalDistribucionDTO update(
        DespliegueInfraestructuraTroncalDistribucionDTO despliegueInfraestructuraTroncalDistribucionDTO
    );

    /**
     * Partially updates a despliegueInfraestructuraTroncalDistribucion.
     *
     * @param despliegueInfraestructuraTroncalDistribucionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DespliegueInfraestructuraTroncalDistribucionDTO> partialUpdate(
        DespliegueInfraestructuraTroncalDistribucionDTO despliegueInfraestructuraTroncalDistribucionDTO
    );

    /**
     * Get all the despliegueInfraestructuraTroncalDistribucions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DespliegueInfraestructuraTroncalDistribucionDTO> findAll(Pageable pageable);

    /**
     * Get all the despliegueInfraestructuraTroncalDistribucions with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DespliegueInfraestructuraTroncalDistribucionDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" despliegueInfraestructuraTroncalDistribucion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DespliegueInfraestructuraTroncalDistribucionDTO> findOne(Long id);

    /**
     * Delete the "id" despliegueInfraestructuraTroncalDistribucion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
