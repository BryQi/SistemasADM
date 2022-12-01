package com.municipio.sistemasadm.web.rest;

import com.municipio.sistemasadm.repository.DespliegueInfraestructuraTroncalDistribucionRepository;
import com.municipio.sistemasadm.service.DespliegueInfraestructuraTroncalDistribucionService;
import com.municipio.sistemasadm.service.dto.DespliegueInfraestructuraTroncalDistribucionDTO;
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
 * REST controller for managing {@link com.municipio.sistemasadm.domain.DespliegueInfraestructuraTroncalDistribucion}.
 */
@RestController
@RequestMapping("/api")
public class DespliegueInfraestructuraTroncalDistribucionResource {

    private final Logger log = LoggerFactory.getLogger(DespliegueInfraestructuraTroncalDistribucionResource.class);

    private static final String ENTITY_NAME = "despliegueInfraestructuraTroncalDistribucion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DespliegueInfraestructuraTroncalDistribucionService despliegueInfraestructuraTroncalDistribucionService;

    private final DespliegueInfraestructuraTroncalDistribucionRepository despliegueInfraestructuraTroncalDistribucionRepository;

    public DespliegueInfraestructuraTroncalDistribucionResource(
        DespliegueInfraestructuraTroncalDistribucionService despliegueInfraestructuraTroncalDistribucionService,
        DespliegueInfraestructuraTroncalDistribucionRepository despliegueInfraestructuraTroncalDistribucionRepository
    ) {
        this.despliegueInfraestructuraTroncalDistribucionService = despliegueInfraestructuraTroncalDistribucionService;
        this.despliegueInfraestructuraTroncalDistribucionRepository = despliegueInfraestructuraTroncalDistribucionRepository;
    }

    /**
     * {@code POST  /despliegue-infraestructura-troncal-distribucions} : Create a new despliegueInfraestructuraTroncalDistribucion.
     *
     * @param despliegueInfraestructuraTroncalDistribucionDTO the despliegueInfraestructuraTroncalDistribucionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new despliegueInfraestructuraTroncalDistribucionDTO, or with status {@code 400 (Bad Request)} if the despliegueInfraestructuraTroncalDistribucion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/despliegue-infraestructura-troncal-distribucions")
    public ResponseEntity<DespliegueInfraestructuraTroncalDistribucionDTO> createDespliegueInfraestructuraTroncalDistribucion(
        @Valid @RequestBody DespliegueInfraestructuraTroncalDistribucionDTO despliegueInfraestructuraTroncalDistribucionDTO
    ) throws URISyntaxException {
        log.debug(
            "REST request to save DespliegueInfraestructuraTroncalDistribucion : {}",
            despliegueInfraestructuraTroncalDistribucionDTO
        );
        if (despliegueInfraestructuraTroncalDistribucionDTO.getId() != null) {
            throw new BadRequestAlertException(
                "A new despliegueInfraestructuraTroncalDistribucion cannot already have an ID",
                ENTITY_NAME,
                "idexists"
            );
        }
        DespliegueInfraestructuraTroncalDistribucionDTO result = despliegueInfraestructuraTroncalDistribucionService.save(
            despliegueInfraestructuraTroncalDistribucionDTO
        );
        return ResponseEntity
            .created(new URI("/api/despliegue-infraestructura-troncal-distribucions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /despliegue-infraestructura-troncal-distribucions/:id} : Updates an existing despliegueInfraestructuraTroncalDistribucion.
     *
     * @param id the id of the despliegueInfraestructuraTroncalDistribucionDTO to save.
     * @param despliegueInfraestructuraTroncalDistribucionDTO the despliegueInfraestructuraTroncalDistribucionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated despliegueInfraestructuraTroncalDistribucionDTO,
     * or with status {@code 400 (Bad Request)} if the despliegueInfraestructuraTroncalDistribucionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the despliegueInfraestructuraTroncalDistribucionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/despliegue-infraestructura-troncal-distribucions/{id}")
    public ResponseEntity<DespliegueInfraestructuraTroncalDistribucionDTO> updateDespliegueInfraestructuraTroncalDistribucion(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DespliegueInfraestructuraTroncalDistribucionDTO despliegueInfraestructuraTroncalDistribucionDTO
    ) throws URISyntaxException {
        log.debug(
            "REST request to update DespliegueInfraestructuraTroncalDistribucion : {}, {}",
            id,
            despliegueInfraestructuraTroncalDistribucionDTO
        );
        if (despliegueInfraestructuraTroncalDistribucionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, despliegueInfraestructuraTroncalDistribucionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!despliegueInfraestructuraTroncalDistribucionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DespliegueInfraestructuraTroncalDistribucionDTO result = despliegueInfraestructuraTroncalDistribucionService.update(
            despliegueInfraestructuraTroncalDistribucionDTO
        );
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    despliegueInfraestructuraTroncalDistribucionDTO.getId().toString()
                )
            )
            .body(result);
    }

    /**
     * {@code PATCH  /despliegue-infraestructura-troncal-distribucions/:id} : Partial updates given fields of an existing despliegueInfraestructuraTroncalDistribucion, field will ignore if it is null
     *
     * @param id the id of the despliegueInfraestructuraTroncalDistribucionDTO to save.
     * @param despliegueInfraestructuraTroncalDistribucionDTO the despliegueInfraestructuraTroncalDistribucionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated despliegueInfraestructuraTroncalDistribucionDTO,
     * or with status {@code 400 (Bad Request)} if the despliegueInfraestructuraTroncalDistribucionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the despliegueInfraestructuraTroncalDistribucionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the despliegueInfraestructuraTroncalDistribucionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(
        value = "/despliegue-infraestructura-troncal-distribucions/{id}",
        consumes = { "application/json", "application/merge-patch+json" }
    )
    public ResponseEntity<DespliegueInfraestructuraTroncalDistribucionDTO> partialUpdateDespliegueInfraestructuraTroncalDistribucion(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DespliegueInfraestructuraTroncalDistribucionDTO despliegueInfraestructuraTroncalDistribucionDTO
    ) throws URISyntaxException {
        log.debug(
            "REST request to partial update DespliegueInfraestructuraTroncalDistribucion partially : {}, {}",
            id,
            despliegueInfraestructuraTroncalDistribucionDTO
        );
        if (despliegueInfraestructuraTroncalDistribucionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, despliegueInfraestructuraTroncalDistribucionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!despliegueInfraestructuraTroncalDistribucionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DespliegueInfraestructuraTroncalDistribucionDTO> result = despliegueInfraestructuraTroncalDistribucionService.partialUpdate(
            despliegueInfraestructuraTroncalDistribucionDTO
        );

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(
                applicationName,
                true,
                ENTITY_NAME,
                despliegueInfraestructuraTroncalDistribucionDTO.getId().toString()
            )
        );
    }

    /**
     * {@code GET  /despliegue-infraestructura-troncal-distribucions} : get all the despliegueInfraestructuraTroncalDistribucions.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of despliegueInfraestructuraTroncalDistribucions in body.
     */
    @GetMapping("/despliegue-infraestructura-troncal-distribucions")
    public ResponseEntity<List<DespliegueInfraestructuraTroncalDistribucionDTO>> getAllDespliegueInfraestructuraTroncalDistribucions(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of DespliegueInfraestructuraTroncalDistribucions");
        Page<DespliegueInfraestructuraTroncalDistribucionDTO> page;
        if (eagerload) {
            page = despliegueInfraestructuraTroncalDistribucionService.findAllWithEagerRelationships(pageable);
        } else {
            page = despliegueInfraestructuraTroncalDistribucionService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /despliegue-infraestructura-troncal-distribucions/:id} : get the "id" despliegueInfraestructuraTroncalDistribucion.
     *
     * @param id the id of the despliegueInfraestructuraTroncalDistribucionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the despliegueInfraestructuraTroncalDistribucionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/despliegue-infraestructura-troncal-distribucions/{id}")
    public ResponseEntity<DespliegueInfraestructuraTroncalDistribucionDTO> getDespliegueInfraestructuraTroncalDistribucion(
        @PathVariable Long id
    ) {
        log.debug("REST request to get DespliegueInfraestructuraTroncalDistribucion : {}", id);
        Optional<DespliegueInfraestructuraTroncalDistribucionDTO> despliegueInfraestructuraTroncalDistribucionDTO = despliegueInfraestructuraTroncalDistribucionService.findOne(
            id
        );
        return ResponseUtil.wrapOrNotFound(despliegueInfraestructuraTroncalDistribucionDTO);
    }

    /**
     * {@code DELETE  /despliegue-infraestructura-troncal-distribucions/:id} : delete the "id" despliegueInfraestructuraTroncalDistribucion.
     *
     * @param id the id of the despliegueInfraestructuraTroncalDistribucionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/despliegue-infraestructura-troncal-distribucions/{id}")
    public ResponseEntity<Void> deleteDespliegueInfraestructuraTroncalDistribucion(@PathVariable Long id) {
        log.debug("REST request to delete DespliegueInfraestructuraTroncalDistribucion : {}", id);
        despliegueInfraestructuraTroncalDistribucionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
