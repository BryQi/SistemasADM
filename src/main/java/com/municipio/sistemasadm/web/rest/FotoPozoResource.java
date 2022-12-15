package com.municipio.sistemasadm.web.rest;

import com.municipio.sistemasadm.repository.FotoPozoRepository;
import com.municipio.sistemasadm.service.FotoPozoService;
import com.municipio.sistemasadm.service.dto.FotoPozoDTO;
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
 * REST controller for managing {@link com.municipio.sistemasadm.domain.FotoPozo}.
 */
@RestController
@RequestMapping("/api")
public class FotoPozoResource {

    private final Logger log = LoggerFactory.getLogger(FotoPozoResource.class);

    private static final String ENTITY_NAME = "fotoPozo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FotoPozoService fotoPozoService;

    private final FotoPozoRepository fotoPozoRepository;

    public FotoPozoResource(FotoPozoService fotoPozoService, FotoPozoRepository fotoPozoRepository) {
        this.fotoPozoService = fotoPozoService;
        this.fotoPozoRepository = fotoPozoRepository;
    }

    /**
     * {@code POST  /foto-pozos} : Create a new fotoPozo.
     *
     * @param fotoPozoDTO the fotoPozoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fotoPozoDTO, or with status {@code 400 (Bad Request)} if the fotoPozo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/foto-pozos")
    public ResponseEntity<FotoPozoDTO> createFotoPozo(@Valid @RequestBody FotoPozoDTO fotoPozoDTO) throws URISyntaxException {
        log.debug("REST request to save FotoPozo : {}", fotoPozoDTO);
        if (fotoPozoDTO.getId() != null) {
            throw new BadRequestAlertException("A new fotoPozo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FotoPozoDTO result = fotoPozoService.save(fotoPozoDTO);
        return ResponseEntity
            .created(new URI("/api/foto-pozos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /foto-pozos/:id} : Updates an existing fotoPozo.
     *
     * @param id the id of the fotoPozoDTO to save.
     * @param fotoPozoDTO the fotoPozoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fotoPozoDTO,
     * or with status {@code 400 (Bad Request)} if the fotoPozoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fotoPozoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/foto-pozos/{id}")
    public ResponseEntity<FotoPozoDTO> updateFotoPozo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FotoPozoDTO fotoPozoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FotoPozo : {}, {}", id, fotoPozoDTO);
        if (fotoPozoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fotoPozoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fotoPozoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FotoPozoDTO result = fotoPozoService.update(fotoPozoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fotoPozoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /foto-pozos/:id} : Partial updates given fields of an existing fotoPozo, field will ignore if it is null
     *
     * @param id the id of the fotoPozoDTO to save.
     * @param fotoPozoDTO the fotoPozoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fotoPozoDTO,
     * or with status {@code 400 (Bad Request)} if the fotoPozoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fotoPozoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fotoPozoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/foto-pozos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FotoPozoDTO> partialUpdateFotoPozo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FotoPozoDTO fotoPozoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FotoPozo partially : {}, {}", id, fotoPozoDTO);
        if (fotoPozoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fotoPozoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fotoPozoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FotoPozoDTO> result = fotoPozoService.partialUpdate(fotoPozoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fotoPozoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /foto-pozos} : get all the fotoPozos.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fotoPozos in body.
     */
    @GetMapping("/foto-pozos")
    public ResponseEntity<List<FotoPozoDTO>> getAllFotoPozos(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of FotoPozos");
        Page<FotoPozoDTO> page;
        if (eagerload) {
            page = fotoPozoService.findAllWithEagerRelationships(pageable);
        } else {
            page = fotoPozoService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /foto-pozos/:id} : get the "id" fotoPozo.
     *
     * @param id the id of the fotoPozoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fotoPozoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/foto-pozos/{id}")
    public ResponseEntity<FotoPozoDTO> getFotoPozo(@PathVariable Long id) {
        log.debug("REST request to get FotoPozo : {}", id);
        Optional<FotoPozoDTO> fotoPozoDTO = fotoPozoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fotoPozoDTO);
    }

    /**
     * {@code DELETE  /foto-pozos/:id} : delete the "id" fotoPozo.
     *
     * @param id the id of the fotoPozoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/foto-pozos/{id}")
    public ResponseEntity<Void> deleteFotoPozo(@PathVariable Long id) {
        log.debug("REST request to delete FotoPozo : {}", id);
        fotoPozoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
