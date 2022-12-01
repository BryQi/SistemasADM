package com.municipio.sistemasadm.web.rest;

import com.municipio.sistemasadm.repository.InfraestructuraRepository;
import com.municipio.sistemasadm.service.InfraestructuraService;
import com.municipio.sistemasadm.service.dto.InfraestructuraDTO;
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
 * REST controller for managing {@link com.municipio.sistemasadm.domain.Infraestructura}.
 */
@RestController
@RequestMapping("/api")
public class InfraestructuraResource {

    private final Logger log = LoggerFactory.getLogger(InfraestructuraResource.class);

    private static final String ENTITY_NAME = "infraestructura";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InfraestructuraService infraestructuraService;

    private final InfraestructuraRepository infraestructuraRepository;

    public InfraestructuraResource(InfraestructuraService infraestructuraService, InfraestructuraRepository infraestructuraRepository) {
        this.infraestructuraService = infraestructuraService;
        this.infraestructuraRepository = infraestructuraRepository;
    }

    /**
     * {@code POST  /infraestructuras} : Create a new infraestructura.
     *
     * @param infraestructuraDTO the infraestructuraDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new infraestructuraDTO, or with status {@code 400 (Bad Request)} if the infraestructura has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/infraestructuras")
    public ResponseEntity<InfraestructuraDTO> createInfraestructura(@Valid @RequestBody InfraestructuraDTO infraestructuraDTO)
        throws URISyntaxException {
        log.debug("REST request to save Infraestructura : {}", infraestructuraDTO);
        if (infraestructuraDTO.getId() != null) {
            throw new BadRequestAlertException("A new infraestructura cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InfraestructuraDTO result = infraestructuraService.save(infraestructuraDTO);
        return ResponseEntity
            .created(new URI("/api/infraestructuras/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /infraestructuras/:id} : Updates an existing infraestructura.
     *
     * @param id the id of the infraestructuraDTO to save.
     * @param infraestructuraDTO the infraestructuraDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated infraestructuraDTO,
     * or with status {@code 400 (Bad Request)} if the infraestructuraDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the infraestructuraDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/infraestructuras/{id}")
    public ResponseEntity<InfraestructuraDTO> updateInfraestructura(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody InfraestructuraDTO infraestructuraDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Infraestructura : {}, {}", id, infraestructuraDTO);
        if (infraestructuraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, infraestructuraDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!infraestructuraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        InfraestructuraDTO result = infraestructuraService.update(infraestructuraDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, infraestructuraDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /infraestructuras/:id} : Partial updates given fields of an existing infraestructura, field will ignore if it is null
     *
     * @param id the id of the infraestructuraDTO to save.
     * @param infraestructuraDTO the infraestructuraDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated infraestructuraDTO,
     * or with status {@code 400 (Bad Request)} if the infraestructuraDTO is not valid,
     * or with status {@code 404 (Not Found)} if the infraestructuraDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the infraestructuraDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/infraestructuras/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InfraestructuraDTO> partialUpdateInfraestructura(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody InfraestructuraDTO infraestructuraDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Infraestructura partially : {}, {}", id, infraestructuraDTO);
        if (infraestructuraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, infraestructuraDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!infraestructuraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InfraestructuraDTO> result = infraestructuraService.partialUpdate(infraestructuraDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, infraestructuraDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /infraestructuras} : get all the infraestructuras.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of infraestructuras in body.
     */
    @GetMapping("/infraestructuras")
    public ResponseEntity<List<InfraestructuraDTO>> getAllInfraestructuras(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Infraestructuras");
        Page<InfraestructuraDTO> page;
        if (eagerload) {
            page = infraestructuraService.findAllWithEagerRelationships(pageable);
        } else {
            page = infraestructuraService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /infraestructuras/:id} : get the "id" infraestructura.
     *
     * @param id the id of the infraestructuraDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the infraestructuraDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/infraestructuras/{id}")
    public ResponseEntity<InfraestructuraDTO> getInfraestructura(@PathVariable Long id) {
        log.debug("REST request to get Infraestructura : {}", id);
        Optional<InfraestructuraDTO> infraestructuraDTO = infraestructuraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(infraestructuraDTO);
    }

    /**
     * {@code DELETE  /infraestructuras/:id} : delete the "id" infraestructura.
     *
     * @param id the id of the infraestructuraDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/infraestructuras/{id}")
    public ResponseEntity<Void> deleteInfraestructura(@PathVariable Long id) {
        log.debug("REST request to delete Infraestructura : {}", id);
        infraestructuraService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
