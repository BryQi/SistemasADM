package com.municipio.sistemasadm.service;

import com.municipio.sistemasadm.service.dto.DespliegueinfraestructuradispersionDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.municipio.sistemasadm.domain.Despliegueinfraestructuradispersion}.
 */
public interface DespliegueinfraestructuradispersionService {
    /**
     * Save a despliegueinfraestructuradispersion.
     *
     * @param despliegueinfraestructuradispersionDTO the entity to save.
     * @return the persisted entity.
     */
    DespliegueinfraestructuradispersionDTO save(DespliegueinfraestructuradispersionDTO despliegueinfraestructuradispersionDTO);

    /**
     * Updates a despliegueinfraestructuradispersion.
     *
     * @param despliegueinfraestructuradispersionDTO the entity to update.
     * @return the persisted entity.
     */
    DespliegueinfraestructuradispersionDTO update(DespliegueinfraestructuradispersionDTO despliegueinfraestructuradispersionDTO);

    /**
     * Partially updates a despliegueinfraestructuradispersion.
     *
     * @param despliegueinfraestructuradispersionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DespliegueinfraestructuradispersionDTO> partialUpdate(
        DespliegueinfraestructuradispersionDTO despliegueinfraestructuradispersionDTO
    );

    /**
     * Get all the despliegueinfraestructuradispersions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DespliegueinfraestructuradispersionDTO> findAll(Pageable pageable);

    /**
     * Get all the despliegueinfraestructuradispersions with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DespliegueinfraestructuradispersionDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" despliegueinfraestructuradispersion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DespliegueinfraestructuradispersionDTO> findOne(Long id);

    /**
     * Delete the "id" despliegueinfraestructuradispersion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
