package com.municipio.sistemasadm.service;

import com.municipio.sistemasadm.service.dto.RegistroInspeccionesDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.municipio.sistemasadm.domain.RegistroInspecciones}.
 */
public interface RegistroInspeccionesService {
    /**
     * Save a registroInspecciones.
     *
     * @param registroInspeccionesDTO the entity to save.
     * @return the persisted entity.
     */
    RegistroInspeccionesDTO save(RegistroInspeccionesDTO registroInspeccionesDTO);

    /**
     * Updates a registroInspecciones.
     *
     * @param registroInspeccionesDTO the entity to update.
     * @return the persisted entity.
     */
    RegistroInspeccionesDTO update(RegistroInspeccionesDTO registroInspeccionesDTO);

    /**
     * Partially updates a registroInspecciones.
     *
     * @param registroInspeccionesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RegistroInspeccionesDTO> partialUpdate(RegistroInspeccionesDTO registroInspeccionesDTO);

    /**
     * Get all the registroInspecciones.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RegistroInspeccionesDTO> findAll(Pageable pageable);

    /**
     * Get the "id" registroInspecciones.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RegistroInspeccionesDTO> findOne(Long id);

    /**
     * Delete the "id" registroInspecciones.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
