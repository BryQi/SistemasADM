package com.municipio.sistemasadm.web.rest;

import com.municipio.sistemasadm.repository.PozoRepository;
import com.municipio.sistemasadm.service.PozoService;
import com.municipio.sistemasadm.service.dto.PozoDTO;
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
 * REST controller for managing {@link com.municipio.sistemasadm.domain.Pozo}.
 */
@RestController
@RequestMapping("/api")
public class PozoResource {

    private final Logger log = LoggerFactory.getLogger(PozoResource.class);

    private static final String ENTITY_NAME = "pozo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PozoService pozoService;

    private final PozoRepository pozoRepository;

    public PozoResource(PozoService pozoService, PozoRepository pozoRepository) {
        this.pozoService = pozoService;
        this.pozoRepository = pozoRepository;
    }

    /**
     * {@code POST  /pozos} : Create a new pozo.
     *
     * @param pozoDTO the pozoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pozoDTO, or with status {@code 400 (Bad Request)} if the pozo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pozos")
    public ResponseEntity<PozoDTO> createPozo(@Valid @RequestBody PozoDTO pozoDTO) throws URISyntaxException {
        log.debug("REST request to save Pozo : {}", pozoDTO);
        if (pozoDTO.getId() != null) {
            throw new BadRequestAlertException("A new pozo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PozoDTO result = pozoService.save(pozoDTO);
        return ResponseEntity
            .created(new URI("/api/pozos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pozos/:id} : Updates an existing pozo.
     *
     * @param id the id of the pozoDTO to save.
     * @param pozoDTO the pozoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pozoDTO,
     * or with status {@code 400 (Bad Request)} if the pozoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pozoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pozos/{id}")
    public ResponseEntity<PozoDTO> updatePozo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PozoDTO pozoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Pozo : {}, {}", id, pozoDTO);
        if (pozoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pozoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pozoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PozoDTO result = pozoService.update(pozoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pozoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /pozos/:id} : Partial updates given fields of an existing pozo, field will ignore if it is null
     *
     * @param id the id of the pozoDTO to save.
     * @param pozoDTO the pozoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pozoDTO,
     * or with status {@code 400 (Bad Request)} if the pozoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the pozoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the pozoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/pozos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PozoDTO> partialUpdatePozo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PozoDTO pozoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Pozo partially : {}, {}", id, pozoDTO);
        if (pozoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pozoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pozoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PozoDTO> result = pozoService.partialUpdate(pozoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pozoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /pozos} : get all the pozos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pozos in body.
     */
    @GetMapping("/pozos")
    public ResponseEntity<List<PozoDTO>> getAllPozos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Pozos");
        Page<PozoDTO> page = pozoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pozos/:id} : get the "id" pozo.
     *
     * @param id the id of the pozoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pozoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pozos/{id}")
    public ResponseEntity<PozoDTO> getPozo(@PathVariable Long id) {
        log.debug("REST request to get Pozo : {}", id);
        Optional<PozoDTO> pozoDTO = pozoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pozoDTO);
    }

    /**
     * {@code DELETE  /pozos/:id} : delete the "id" pozo.
     *
     * @param id the id of the pozoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pozos/{id}")
    public ResponseEntity<Void> deletePozo(@PathVariable Long id) {
        log.debug("REST request to delete Pozo : {}", id);
        pozoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
