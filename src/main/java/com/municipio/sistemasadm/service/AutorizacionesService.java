package com.municipio.sistemasadm.service;

import com.municipio.sistemasadm.service.dto.AutorizacionesDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.municipio.sistemasadm.domain.Autorizaciones}.
 */
public interface AutorizacionesService {
    /**
     * Save a autorizaciones.
     *
     * @param autorizacionesDTO the entity to save.
     * @return the persisted entity.
     */
    AutorizacionesDTO save(AutorizacionesDTO autorizacionesDTO);

    /**
     * Updates a autorizaciones.
     *
     * @param autorizacionesDTO the entity to update.
     * @return the persisted entity.
     */
    AutorizacionesDTO update(AutorizacionesDTO autorizacionesDTO);

    /**
     * Partially updates a autorizaciones.
     *
     * @param autorizacionesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AutorizacionesDTO> partialUpdate(AutorizacionesDTO autorizacionesDTO);

    /**
     * Get all the autorizaciones.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AutorizacionesDTO> findAll(Pageable pageable);

    /**
     * Get the "id" autorizaciones.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AutorizacionesDTO> findOne(Long id);

    /**
     * Delete the "id" autorizaciones.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
