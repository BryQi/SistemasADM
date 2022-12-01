package com.municipio.sistemasadm.service;

import com.municipio.sistemasadm.service.dto.PozoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.municipio.sistemasadm.domain.Pozo}.
 */
public interface PozoService {
    /**
     * Save a pozo.
     *
     * @param pozoDTO the entity to save.
     * @return the persisted entity.
     */
    PozoDTO save(PozoDTO pozoDTO);

    /**
     * Updates a pozo.
     *
     * @param pozoDTO the entity to update.
     * @return the persisted entity.
     */
    PozoDTO update(PozoDTO pozoDTO);

    /**
     * Partially updates a pozo.
     *
     * @param pozoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PozoDTO> partialUpdate(PozoDTO pozoDTO);

    /**
     * Get all the pozos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PozoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" pozo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PozoDTO> findOne(Long id);

    /**
     * Delete the "id" pozo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
