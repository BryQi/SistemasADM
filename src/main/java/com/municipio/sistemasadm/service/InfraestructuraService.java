package com.municipio.sistemasadm.service;

import com.municipio.sistemasadm.service.dto.InfraestructuraDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.municipio.sistemasadm.domain.Infraestructura}.
 */
public interface InfraestructuraService {
    /**
     * Save a infraestructura.
     *
     * @param infraestructuraDTO the entity to save.
     * @return the persisted entity.
     */
    InfraestructuraDTO save(InfraestructuraDTO infraestructuraDTO);

    /**
     * Updates a infraestructura.
     *
     * @param infraestructuraDTO the entity to update.
     * @return the persisted entity.
     */
    InfraestructuraDTO update(InfraestructuraDTO infraestructuraDTO);

    /**
     * Partially updates a infraestructura.
     *
     * @param infraestructuraDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<InfraestructuraDTO> partialUpdate(InfraestructuraDTO infraestructuraDTO);

    /**
     * Get all the infraestructuras.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InfraestructuraDTO> findAll(Pageable pageable);

    /**
     * Get all the infraestructuras with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InfraestructuraDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" infraestructura.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InfraestructuraDTO> findOne(Long id);

    /**
     * Delete the "id" infraestructura.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
