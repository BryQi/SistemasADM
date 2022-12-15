package com.municipio.sistemasadm.web.rest;

import com.municipio.sistemasadm.repository.AutorizacionesRepository;
import com.municipio.sistemasadm.service.AutorizacionesService;
import com.municipio.sistemasadm.service.dto.AutorizacionesDTO;
import com.municipio.sistemasadm.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.municipio.sistemasadm.domain.Autorizaciones}.
 */
@RestController
@RequestMapping("/api")
public class AutorizacionesResource {

    private final Logger log = LoggerFactory.getLogger(AutorizacionesResource.class);

    private static final String ENTITY_NAME = "autorizaciones";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AutorizacionesService autorizacionesService;

    private final AutorizacionesRepository autorizacionesRepository;

    public AutorizacionesResource(AutorizacionesService autorizacionesService, AutorizacionesRepository autorizacionesRepository) {
        this.autorizacionesService = autorizacionesService;
        this.autorizacionesRepository = autorizacionesRepository;
    }

    /**
     * {@code POST  /autorizaciones} : Create a new autorizaciones.
     *
     * @param autorizacionesDTO the autorizacionesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new autorizacionesDTO, or with status {@code 400 (Bad Request)} if the autorizaciones has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/autorizaciones")
    public ResponseEntity<AutorizacionesDTO> createAutorizaciones(@Valid @RequestBody AutorizacionesDTO autorizacionesDTO)
        throws URISyntaxException {
        log.debug("REST request to save Autorizaciones : {}", autorizacionesDTO);
        if (autorizacionesDTO.getId() != null) {
            throw new BadRequestAlertException("A new autorizaciones cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AutorizacionesDTO result = autorizacionesService.save(autorizacionesDTO);
        return ResponseEntity
            .created(new URI("/api/autorizaciones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /autorizaciones/:id} : Updates an existing autorizaciones.
     *
     * @param id the id of the autorizacionesDTO to save.
     * @param autorizacionesDTO the autorizacionesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autorizacionesDTO,
     * or with status {@code 400 (Bad Request)} if the autorizacionesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the autorizacionesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/autorizaciones/{id}")
    public ResponseEntity<AutorizacionesDTO> updateAutorizaciones(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AutorizacionesDTO autorizacionesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Autorizaciones : {}, {}", id, autorizacionesDTO);
        if (autorizacionesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, autorizacionesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!autorizacionesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AutorizacionesDTO result = autorizacionesService.update(autorizacionesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, autorizacionesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /autorizaciones/:id} : Partial updates given fields of an existing autorizaciones, field will ignore if it is null
     *
     * @param id the id of the autorizacionesDTO to save.
     * @param autorizacionesDTO the autorizacionesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated autorizacionesDTO,
     * or with status {@code 400 (Bad Request)} if the autorizacionesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the autorizacionesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the autorizacionesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/autorizaciones/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AutorizacionesDTO> partialUpdateAutorizaciones(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AutorizacionesDTO autorizacionesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Autorizaciones partially : {}, {}", id, autorizacionesDTO);
        if (autorizacionesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, autorizacionesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!autorizacionesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AutorizacionesDTO> result = autorizacionesService.partialUpdate(autorizacionesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, autorizacionesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /autorizaciones} : get all the autorizaciones.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of autorizaciones in body.
     */
    @GetMapping("/autorizaciones")
    public ResponseEntity<List<AutorizacionesDTO>> getAllAutorizaciones(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Autorizaciones");
        Page<AutorizacionesDTO> page;
        if (eagerload) {
            page = autorizacionesService.findAllWithEagerRelationships(pageable);
        } else {
            page = autorizacionesService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /autorizaciones/:id} : get the "id" autorizaciones.
     *
     * @param id the id of the autorizacionesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the autorizacionesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/autorizaciones/{id}")
    public ResponseEntity<AutorizacionesDTO> getAutorizaciones(@PathVariable Long id) {
        log.debug("REST request to get Autorizaciones : {}", id);
        Optional<AutorizacionesDTO> autorizacionesDTO = autorizacionesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(autorizacionesDTO);
    }

    /**
     * {@code DELETE  /autorizaciones/:id} : delete the "id" autorizaciones.
     *
     * @param id the id of the autorizacionesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/autorizaciones/{id}")
    public ResponseEntity<Void> deleteAutorizaciones(@PathVariable Long id) {
        log.debug("REST request to delete Autorizaciones : {}", id);
        autorizacionesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
