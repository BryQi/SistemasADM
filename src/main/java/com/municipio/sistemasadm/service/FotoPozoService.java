package com.municipio.sistemasadm.service;

import com.municipio.sistemasadm.service.dto.FotoPozoDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.municipio.sistemasadm.domain.FotoPozo}.
 */
public interface FotoPozoService {
    /**
     * Save a fotoPozo.
     *
     * @param fotoPozoDTO the entity to save.
     * @return the persisted entity.
     */
    FotoPozoDTO save(FotoPozoDTO fotoPozoDTO);

    /**
     * Updates a fotoPozo.
     *
     * @param fotoPozoDTO the entity to update.
     * @return the persisted entity.
     */
    FotoPozoDTO update(FotoPozoDTO fotoPozoDTO);

    /**
     * Partially updates a fotoPozo.
     *
     * @param fotoPozoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FotoPozoDTO> partialUpdate(FotoPozoDTO fotoPozoDTO);

    /**
     * Get all the fotoPozos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FotoPozoDTO> findAll(Pageable pageable);

    /**
     * Get all the fotoPozos with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FotoPozoDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" fotoPozo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FotoPozoDTO> findOne(Long id);

    /**
     * Delete the "id" fotoPozo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
