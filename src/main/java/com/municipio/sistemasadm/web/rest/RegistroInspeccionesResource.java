package com.municipio.sistemasadm.web.rest;

import com.municipio.sistemasadm.repository.RegistroInspeccionesRepository;
import com.municipio.sistemasadm.service.RegistroInspeccionesService;
import com.municipio.sistemasadm.service.dto.RegistroInspeccionesDTO;
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
 * REST controller for managing {@link com.municipio.sistemasadm.domain.RegistroInspecciones}.
 */
@RestController
@RequestMapping("/api")
public class RegistroInspeccionesResource {

    private final Logger log = LoggerFactory.getLogger(RegistroInspeccionesResource.class);

    private static final String ENTITY_NAME = "registroInspecciones";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegistroInspeccionesService registroInspeccionesService;

    private final RegistroInspeccionesRepository registroInspeccionesRepository;

    public RegistroInspeccionesResource(
        RegistroInspeccionesService registroInspeccionesService,
        RegistroInspeccionesRepository registroInspeccionesRepository
    ) {
        this.registroInspeccionesService = registroInspeccionesService;
        this.registroInspeccionesRepository = registroInspeccionesRepository;
    }

    /**
     * {@code POST  /registro-inspecciones} : Create a new registroInspecciones.
     *
     * @param registroInspeccionesDTO the registroInspeccionesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new registroInspeccionesDTO, or with status {@code 400 (Bad Request)} if the registroInspecciones has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/registro-inspecciones")
    public ResponseEntity<RegistroInspeccionesDTO> createRegistroInspecciones(
        @Valid @RequestBody RegistroInspeccionesDTO registroInspeccionesDTO
    ) throws URISyntaxException {
        log.debug("REST request to save RegistroInspecciones : {}", registroInspeccionesDTO);
        if (registroInspeccionesDTO.getId() != null) {
            throw new BadRequestAlertException("A new registroInspecciones cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RegistroInspeccionesDTO result = registroInspeccionesService.save(registroInspeccionesDTO);
        return ResponseEntity
            .created(new URI("/api/registro-inspecciones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /registro-inspecciones/:id} : Updates an existing registroInspecciones.
     *
     * @param id the id of the registroInspeccionesDTO to save.
     * @param registroInspeccionesDTO the registroInspeccionesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated registroInspeccionesDTO,
     * or with status {@code 400 (Bad Request)} if the registroInspeccionesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the registroInspeccionesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/registro-inspecciones/{id}")
    public ResponseEntity<RegistroInspeccionesDTO> updateRegistroInspecciones(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RegistroInspeccionesDTO registroInspeccionesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RegistroInspecciones : {}, {}", id, registroInspeccionesDTO);
        if (registroInspeccionesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, registroInspeccionesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!registroInspeccionesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RegistroInspeccionesDTO result = registroInspeccionesService.update(registroInspeccionesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, registroInspeccionesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /registro-inspecciones/:id} : Partial updates given fields of an existing registroInspecciones, field will ignore if it is null
     *
     * @param id the id of the registroInspeccionesDTO to save.
     * @param registroInspeccionesDTO the registroInspeccionesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated registroInspeccionesDTO,
     * or with status {@code 400 (Bad Request)} if the registroInspeccionesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the registroInspeccionesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the registroInspeccionesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/registro-inspecciones/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RegistroInspeccionesDTO> partialUpdateRegistroInspecciones(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RegistroInspeccionesDTO registroInspeccionesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RegistroInspecciones partially : {}, {}", id, registroInspeccionesDTO);
        if (registroInspeccionesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, registroInspeccionesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!registroInspeccionesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RegistroInspeccionesDTO> result = registroInspeccionesService.partialUpdate(registroInspeccionesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, registroInspeccionesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /registro-inspecciones} : get all the registroInspecciones.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of registroInspecciones in body.
     */
    @GetMapping("/registro-inspecciones")
    public ResponseEntity<List<RegistroInspeccionesDTO>> getAllRegistroInspecciones(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of RegistroInspecciones");
        Page<RegistroInspeccionesDTO> page = registroInspeccionesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /registro-inspecciones/:id} : get the "id" registroInspecciones.
     *
     * @param id the id of the registroInspeccionesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the registroInspeccionesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/registro-inspecciones/{id}")
    public ResponseEntity<RegistroInspeccionesDTO> getRegistroInspecciones(@PathVariable Long id) {
        log.debug("REST request to get RegistroInspecciones : {}", id);
        Optional<RegistroInspeccionesDTO> registroInspeccionesDTO = registroInspeccionesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(registroInspeccionesDTO);
    }

    /**
     * {@code DELETE  /registro-inspecciones/:id} : delete the "id" registroInspecciones.
     *
     * @param id the id of the registroInspeccionesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/registro-inspecciones/{id}")
    public ResponseEntity<Void> deleteRegistroInspecciones(@PathVariable Long id) {
        log.debug("REST request to delete RegistroInspecciones : {}", id);
        registroInspeccionesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
