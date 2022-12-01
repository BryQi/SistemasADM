package com.municipio.sistemasadm.web.rest;

import static com.municipio.sistemasadm.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.municipio.sistemasadm.IntegrationTest;
import com.municipio.sistemasadm.domain.RegistroInspecciones;
import com.municipio.sistemasadm.repository.RegistroInspeccionesRepository;
import com.municipio.sistemasadm.service.dto.RegistroInspeccionesDTO;
import com.municipio.sistemasadm.service.mapper.RegistroInspeccionesMapper;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RegistroInspeccionesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RegistroInspeccionesResourceIT {

    private static final Boolean DEFAULT_CUMPLE_AUTORIZACION = false;
    private static final Boolean UPDATED_CUMPLE_AUTORIZACION = true;

    private static final Integer DEFAULT_NUMERO_AUTORIZACION = 1;
    private static final Integer UPDATED_NUMERO_AUTORIZACION = 2;

    private static final Boolean DEFAULT_CUMPLE_SENALETICA = false;
    private static final Boolean UPDATED_CUMPLE_SENALETICA = true;

    private static final Boolean DEFAULT_CUMPLE_CONOS_SEGURIDAD = false;
    private static final Boolean UPDATED_CUMPLE_CONOS_SEGURIDAD = true;

    private static final Boolean DEFAULT_CUMPLE_ETIQUETADO = false;
    private static final Boolean UPDATED_CUMPLE_ETIQUETADO = true;

    private static final Boolean DEFAULT_CUMPLE_ARREGLO_CABLES = false;
    private static final Boolean UPDATED_CUMPLE_ARREGLO_CABLES = true;

    private static final Boolean DEFAULT_CUMPLELIMPIEZA_ORDEN_POZO = false;
    private static final Boolean UPDATED_CUMPLELIMPIEZA_ORDEN_POZO = true;

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/registro-inspecciones";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RegistroInspeccionesRepository registroInspeccionesRepository;

    @Autowired
    private RegistroInspeccionesMapper registroInspeccionesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRegistroInspeccionesMockMvc;

    private RegistroInspecciones registroInspecciones;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegistroInspecciones createEntity(EntityManager em) {
        RegistroInspecciones registroInspecciones = new RegistroInspecciones()
            .cumpleAutorizacion(DEFAULT_CUMPLE_AUTORIZACION)
            .numeroAutorizacion(DEFAULT_NUMERO_AUTORIZACION)
            .cumpleSenaletica(DEFAULT_CUMPLE_SENALETICA)
            .cumpleConosSeguridad(DEFAULT_CUMPLE_CONOS_SEGURIDAD)
            .cumpleEtiquetado(DEFAULT_CUMPLE_ETIQUETADO)
            .cumpleArregloCables(DEFAULT_CUMPLE_ARREGLO_CABLES)
            .cumplelimpiezaOrdenPozo(DEFAULT_CUMPLELIMPIEZA_ORDEN_POZO)
            .createdAt(DEFAULT_CREATED_AT);
        return registroInspecciones;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegistroInspecciones createUpdatedEntity(EntityManager em) {
        RegistroInspecciones registroInspecciones = new RegistroInspecciones()
            .cumpleAutorizacion(UPDATED_CUMPLE_AUTORIZACION)
            .numeroAutorizacion(UPDATED_NUMERO_AUTORIZACION)
            .cumpleSenaletica(UPDATED_CUMPLE_SENALETICA)
            .cumpleConosSeguridad(UPDATED_CUMPLE_CONOS_SEGURIDAD)
            .cumpleEtiquetado(UPDATED_CUMPLE_ETIQUETADO)
            .cumpleArregloCables(UPDATED_CUMPLE_ARREGLO_CABLES)
            .cumplelimpiezaOrdenPozo(UPDATED_CUMPLELIMPIEZA_ORDEN_POZO)
            .createdAt(UPDATED_CREATED_AT);
        return registroInspecciones;
    }

    @BeforeEach
    public void initTest() {
        registroInspecciones = createEntity(em);
    }

    @Test
    @Transactional
    void createRegistroInspecciones() throws Exception {
        int databaseSizeBeforeCreate = registroInspeccionesRepository.findAll().size();
        // Create the RegistroInspecciones
        RegistroInspeccionesDTO registroInspeccionesDTO = registroInspeccionesMapper.toDto(registroInspecciones);
        restRegistroInspeccionesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(registroInspeccionesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RegistroInspecciones in the database
        List<RegistroInspecciones> registroInspeccionesList = registroInspeccionesRepository.findAll();
        assertThat(registroInspeccionesList).hasSize(databaseSizeBeforeCreate + 1);
        RegistroInspecciones testRegistroInspecciones = registroInspeccionesList.get(registroInspeccionesList.size() - 1);
        assertThat(testRegistroInspecciones.getCumpleAutorizacion()).isEqualTo(DEFAULT_CUMPLE_AUTORIZACION);
        assertThat(testRegistroInspecciones.getNumeroAutorizacion()).isEqualTo(DEFAULT_NUMERO_AUTORIZACION);
        assertThat(testRegistroInspecciones.getCumpleSenaletica()).isEqualTo(DEFAULT_CUMPLE_SENALETICA);
        assertThat(testRegistroInspecciones.getCumpleConosSeguridad()).isEqualTo(DEFAULT_CUMPLE_CONOS_SEGURIDAD);
        assertThat(testRegistroInspecciones.getCumpleEtiquetado()).isEqualTo(DEFAULT_CUMPLE_ETIQUETADO);
        assertThat(testRegistroInspecciones.getCumpleArregloCables()).isEqualTo(DEFAULT_CUMPLE_ARREGLO_CABLES);
        assertThat(testRegistroInspecciones.getCumplelimpiezaOrdenPozo()).isEqualTo(DEFAULT_CUMPLELIMPIEZA_ORDEN_POZO);
        assertThat(testRegistroInspecciones.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    @Transactional
    void createRegistroInspeccionesWithExistingId() throws Exception {
        // Create the RegistroInspecciones with an existing ID
        registroInspecciones.setId(1L);
        RegistroInspeccionesDTO registroInspeccionesDTO = registroInspeccionesMapper.toDto(registroInspecciones);

        int databaseSizeBeforeCreate = registroInspeccionesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegistroInspeccionesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(registroInspeccionesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RegistroInspecciones in the database
        List<RegistroInspecciones> registroInspeccionesList = registroInspeccionesRepository.findAll();
        assertThat(registroInspeccionesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNumeroAutorizacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = registroInspeccionesRepository.findAll().size();
        // set the field null
        registroInspecciones.setNumeroAutorizacion(null);

        // Create the RegistroInspecciones, which fails.
        RegistroInspeccionesDTO registroInspeccionesDTO = registroInspeccionesMapper.toDto(registroInspecciones);

        restRegistroInspeccionesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(registroInspeccionesDTO))
            )
            .andExpect(status().isBadRequest());

        List<RegistroInspecciones> registroInspeccionesList = registroInspeccionesRepository.findAll();
        assertThat(registroInspeccionesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = registroInspeccionesRepository.findAll().size();
        // set the field null
        registroInspecciones.setCreatedAt(null);

        // Create the RegistroInspecciones, which fails.
        RegistroInspeccionesDTO registroInspeccionesDTO = registroInspeccionesMapper.toDto(registroInspecciones);

        restRegistroInspeccionesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(registroInspeccionesDTO))
            )
            .andExpect(status().isBadRequest());

        List<RegistroInspecciones> registroInspeccionesList = registroInspeccionesRepository.findAll();
        assertThat(registroInspeccionesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRegistroInspecciones() throws Exception {
        // Initialize the database
        registroInspeccionesRepository.saveAndFlush(registroInspecciones);

        // Get all the registroInspeccionesList
        restRegistroInspeccionesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(registroInspecciones.getId().intValue())))
            .andExpect(jsonPath("$.[*].cumpleAutorizacion").value(hasItem(DEFAULT_CUMPLE_AUTORIZACION.booleanValue())))
            .andExpect(jsonPath("$.[*].numeroAutorizacion").value(hasItem(DEFAULT_NUMERO_AUTORIZACION)))
            .andExpect(jsonPath("$.[*].cumpleSenaletica").value(hasItem(DEFAULT_CUMPLE_SENALETICA.booleanValue())))
            .andExpect(jsonPath("$.[*].cumpleConosSeguridad").value(hasItem(DEFAULT_CUMPLE_CONOS_SEGURIDAD.booleanValue())))
            .andExpect(jsonPath("$.[*].cumpleEtiquetado").value(hasItem(DEFAULT_CUMPLE_ETIQUETADO.booleanValue())))
            .andExpect(jsonPath("$.[*].cumpleArregloCables").value(hasItem(DEFAULT_CUMPLE_ARREGLO_CABLES.booleanValue())))
            .andExpect(jsonPath("$.[*].cumplelimpiezaOrdenPozo").value(hasItem(DEFAULT_CUMPLELIMPIEZA_ORDEN_POZO.booleanValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))));
    }

    @Test
    @Transactional
    void getRegistroInspecciones() throws Exception {
        // Initialize the database
        registroInspeccionesRepository.saveAndFlush(registroInspecciones);

        // Get the registroInspecciones
        restRegistroInspeccionesMockMvc
            .perform(get(ENTITY_API_URL_ID, registroInspecciones.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(registroInspecciones.getId().intValue()))
            .andExpect(jsonPath("$.cumpleAutorizacion").value(DEFAULT_CUMPLE_AUTORIZACION.booleanValue()))
            .andExpect(jsonPath("$.numeroAutorizacion").value(DEFAULT_NUMERO_AUTORIZACION))
            .andExpect(jsonPath("$.cumpleSenaletica").value(DEFAULT_CUMPLE_SENALETICA.booleanValue()))
            .andExpect(jsonPath("$.cumpleConosSeguridad").value(DEFAULT_CUMPLE_CONOS_SEGURIDAD.booleanValue()))
            .andExpect(jsonPath("$.cumpleEtiquetado").value(DEFAULT_CUMPLE_ETIQUETADO.booleanValue()))
            .andExpect(jsonPath("$.cumpleArregloCables").value(DEFAULT_CUMPLE_ARREGLO_CABLES.booleanValue()))
            .andExpect(jsonPath("$.cumplelimpiezaOrdenPozo").value(DEFAULT_CUMPLELIMPIEZA_ORDEN_POZO.booleanValue()))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)));
    }

    @Test
    @Transactional
    void getNonExistingRegistroInspecciones() throws Exception {
        // Get the registroInspecciones
        restRegistroInspeccionesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRegistroInspecciones() throws Exception {
        // Initialize the database
        registroInspeccionesRepository.saveAndFlush(registroInspecciones);

        int databaseSizeBeforeUpdate = registroInspeccionesRepository.findAll().size();

        // Update the registroInspecciones
        RegistroInspecciones updatedRegistroInspecciones = registroInspeccionesRepository.findById(registroInspecciones.getId()).get();
        // Disconnect from session so that the updates on updatedRegistroInspecciones are not directly saved in db
        em.detach(updatedRegistroInspecciones);
        updatedRegistroInspecciones
            .cumpleAutorizacion(UPDATED_CUMPLE_AUTORIZACION)
            .numeroAutorizacion(UPDATED_NUMERO_AUTORIZACION)
            .cumpleSenaletica(UPDATED_CUMPLE_SENALETICA)
            .cumpleConosSeguridad(UPDATED_CUMPLE_CONOS_SEGURIDAD)
            .cumpleEtiquetado(UPDATED_CUMPLE_ETIQUETADO)
            .cumpleArregloCables(UPDATED_CUMPLE_ARREGLO_CABLES)
            .cumplelimpiezaOrdenPozo(UPDATED_CUMPLELIMPIEZA_ORDEN_POZO)
            .createdAt(UPDATED_CREATED_AT);
        RegistroInspeccionesDTO registroInspeccionesDTO = registroInspeccionesMapper.toDto(updatedRegistroInspecciones);

        restRegistroInspeccionesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, registroInspeccionesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(registroInspeccionesDTO))
            )
            .andExpect(status().isOk());

        // Validate the RegistroInspecciones in the database
        List<RegistroInspecciones> registroInspeccionesList = registroInspeccionesRepository.findAll();
        assertThat(registroInspeccionesList).hasSize(databaseSizeBeforeUpdate);
        RegistroInspecciones testRegistroInspecciones = registroInspeccionesList.get(registroInspeccionesList.size() - 1);
        assertThat(testRegistroInspecciones.getCumpleAutorizacion()).isEqualTo(UPDATED_CUMPLE_AUTORIZACION);
        assertThat(testRegistroInspecciones.getNumeroAutorizacion()).isEqualTo(UPDATED_NUMERO_AUTORIZACION);
        assertThat(testRegistroInspecciones.getCumpleSenaletica()).isEqualTo(UPDATED_CUMPLE_SENALETICA);
        assertThat(testRegistroInspecciones.getCumpleConosSeguridad()).isEqualTo(UPDATED_CUMPLE_CONOS_SEGURIDAD);
        assertThat(testRegistroInspecciones.getCumpleEtiquetado()).isEqualTo(UPDATED_CUMPLE_ETIQUETADO);
        assertThat(testRegistroInspecciones.getCumpleArregloCables()).isEqualTo(UPDATED_CUMPLE_ARREGLO_CABLES);
        assertThat(testRegistroInspecciones.getCumplelimpiezaOrdenPozo()).isEqualTo(UPDATED_CUMPLELIMPIEZA_ORDEN_POZO);
        assertThat(testRegistroInspecciones.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingRegistroInspecciones() throws Exception {
        int databaseSizeBeforeUpdate = registroInspeccionesRepository.findAll().size();
        registroInspecciones.setId(count.incrementAndGet());

        // Create the RegistroInspecciones
        RegistroInspeccionesDTO registroInspeccionesDTO = registroInspeccionesMapper.toDto(registroInspecciones);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegistroInspeccionesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, registroInspeccionesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(registroInspeccionesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RegistroInspecciones in the database
        List<RegistroInspecciones> registroInspeccionesList = registroInspeccionesRepository.findAll();
        assertThat(registroInspeccionesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRegistroInspecciones() throws Exception {
        int databaseSizeBeforeUpdate = registroInspeccionesRepository.findAll().size();
        registroInspecciones.setId(count.incrementAndGet());

        // Create the RegistroInspecciones
        RegistroInspeccionesDTO registroInspeccionesDTO = registroInspeccionesMapper.toDto(registroInspecciones);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegistroInspeccionesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(registroInspeccionesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RegistroInspecciones in the database
        List<RegistroInspecciones> registroInspeccionesList = registroInspeccionesRepository.findAll();
        assertThat(registroInspeccionesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRegistroInspecciones() throws Exception {
        int databaseSizeBeforeUpdate = registroInspeccionesRepository.findAll().size();
        registroInspecciones.setId(count.incrementAndGet());

        // Create the RegistroInspecciones
        RegistroInspeccionesDTO registroInspeccionesDTO = registroInspeccionesMapper.toDto(registroInspecciones);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegistroInspeccionesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(registroInspeccionesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RegistroInspecciones in the database
        List<RegistroInspecciones> registroInspeccionesList = registroInspeccionesRepository.findAll();
        assertThat(registroInspeccionesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRegistroInspeccionesWithPatch() throws Exception {
        // Initialize the database
        registroInspeccionesRepository.saveAndFlush(registroInspecciones);

        int databaseSizeBeforeUpdate = registroInspeccionesRepository.findAll().size();

        // Update the registroInspecciones using partial update
        RegistroInspecciones partialUpdatedRegistroInspecciones = new RegistroInspecciones();
        partialUpdatedRegistroInspecciones.setId(registroInspecciones.getId());

        partialUpdatedRegistroInspecciones
            .cumpleAutorizacion(UPDATED_CUMPLE_AUTORIZACION)
            .numeroAutorizacion(UPDATED_NUMERO_AUTORIZACION)
            .cumpleSenaletica(UPDATED_CUMPLE_SENALETICA)
            .cumpleConosSeguridad(UPDATED_CUMPLE_CONOS_SEGURIDAD)
            .cumpleEtiquetado(UPDATED_CUMPLE_ETIQUETADO)
            .cumpleArregloCables(UPDATED_CUMPLE_ARREGLO_CABLES)
            .cumplelimpiezaOrdenPozo(UPDATED_CUMPLELIMPIEZA_ORDEN_POZO);

        restRegistroInspeccionesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRegistroInspecciones.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRegistroInspecciones))
            )
            .andExpect(status().isOk());

        // Validate the RegistroInspecciones in the database
        List<RegistroInspecciones> registroInspeccionesList = registroInspeccionesRepository.findAll();
        assertThat(registroInspeccionesList).hasSize(databaseSizeBeforeUpdate);
        RegistroInspecciones testRegistroInspecciones = registroInspeccionesList.get(registroInspeccionesList.size() - 1);
        assertThat(testRegistroInspecciones.getCumpleAutorizacion()).isEqualTo(UPDATED_CUMPLE_AUTORIZACION);
        assertThat(testRegistroInspecciones.getNumeroAutorizacion()).isEqualTo(UPDATED_NUMERO_AUTORIZACION);
        assertThat(testRegistroInspecciones.getCumpleSenaletica()).isEqualTo(UPDATED_CUMPLE_SENALETICA);
        assertThat(testRegistroInspecciones.getCumpleConosSeguridad()).isEqualTo(UPDATED_CUMPLE_CONOS_SEGURIDAD);
        assertThat(testRegistroInspecciones.getCumpleEtiquetado()).isEqualTo(UPDATED_CUMPLE_ETIQUETADO);
        assertThat(testRegistroInspecciones.getCumpleArregloCables()).isEqualTo(UPDATED_CUMPLE_ARREGLO_CABLES);
        assertThat(testRegistroInspecciones.getCumplelimpiezaOrdenPozo()).isEqualTo(UPDATED_CUMPLELIMPIEZA_ORDEN_POZO);
        assertThat(testRegistroInspecciones.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateRegistroInspeccionesWithPatch() throws Exception {
        // Initialize the database
        registroInspeccionesRepository.saveAndFlush(registroInspecciones);

        int databaseSizeBeforeUpdate = registroInspeccionesRepository.findAll().size();

        // Update the registroInspecciones using partial update
        RegistroInspecciones partialUpdatedRegistroInspecciones = new RegistroInspecciones();
        partialUpdatedRegistroInspecciones.setId(registroInspecciones.getId());

        partialUpdatedRegistroInspecciones
            .cumpleAutorizacion(UPDATED_CUMPLE_AUTORIZACION)
            .numeroAutorizacion(UPDATED_NUMERO_AUTORIZACION)
            .cumpleSenaletica(UPDATED_CUMPLE_SENALETICA)
            .cumpleConosSeguridad(UPDATED_CUMPLE_CONOS_SEGURIDAD)
            .cumpleEtiquetado(UPDATED_CUMPLE_ETIQUETADO)
            .cumpleArregloCables(UPDATED_CUMPLE_ARREGLO_CABLES)
            .cumplelimpiezaOrdenPozo(UPDATED_CUMPLELIMPIEZA_ORDEN_POZO)
            .createdAt(UPDATED_CREATED_AT);

        restRegistroInspeccionesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRegistroInspecciones.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRegistroInspecciones))
            )
            .andExpect(status().isOk());

        // Validate the RegistroInspecciones in the database
        List<RegistroInspecciones> registroInspeccionesList = registroInspeccionesRepository.findAll();
        assertThat(registroInspeccionesList).hasSize(databaseSizeBeforeUpdate);
        RegistroInspecciones testRegistroInspecciones = registroInspeccionesList.get(registroInspeccionesList.size() - 1);
        assertThat(testRegistroInspecciones.getCumpleAutorizacion()).isEqualTo(UPDATED_CUMPLE_AUTORIZACION);
        assertThat(testRegistroInspecciones.getNumeroAutorizacion()).isEqualTo(UPDATED_NUMERO_AUTORIZACION);
        assertThat(testRegistroInspecciones.getCumpleSenaletica()).isEqualTo(UPDATED_CUMPLE_SENALETICA);
        assertThat(testRegistroInspecciones.getCumpleConosSeguridad()).isEqualTo(UPDATED_CUMPLE_CONOS_SEGURIDAD);
        assertThat(testRegistroInspecciones.getCumpleEtiquetado()).isEqualTo(UPDATED_CUMPLE_ETIQUETADO);
        assertThat(testRegistroInspecciones.getCumpleArregloCables()).isEqualTo(UPDATED_CUMPLE_ARREGLO_CABLES);
        assertThat(testRegistroInspecciones.getCumplelimpiezaOrdenPozo()).isEqualTo(UPDATED_CUMPLELIMPIEZA_ORDEN_POZO);
        assertThat(testRegistroInspecciones.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingRegistroInspecciones() throws Exception {
        int databaseSizeBeforeUpdate = registroInspeccionesRepository.findAll().size();
        registroInspecciones.setId(count.incrementAndGet());

        // Create the RegistroInspecciones
        RegistroInspeccionesDTO registroInspeccionesDTO = registroInspeccionesMapper.toDto(registroInspecciones);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegistroInspeccionesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, registroInspeccionesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(registroInspeccionesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RegistroInspecciones in the database
        List<RegistroInspecciones> registroInspeccionesList = registroInspeccionesRepository.findAll();
        assertThat(registroInspeccionesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRegistroInspecciones() throws Exception {
        int databaseSizeBeforeUpdate = registroInspeccionesRepository.findAll().size();
        registroInspecciones.setId(count.incrementAndGet());

        // Create the RegistroInspecciones
        RegistroInspeccionesDTO registroInspeccionesDTO = registroInspeccionesMapper.toDto(registroInspecciones);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegistroInspeccionesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(registroInspeccionesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RegistroInspecciones in the database
        List<RegistroInspecciones> registroInspeccionesList = registroInspeccionesRepository.findAll();
        assertThat(registroInspeccionesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRegistroInspecciones() throws Exception {
        int databaseSizeBeforeUpdate = registroInspeccionesRepository.findAll().size();
        registroInspecciones.setId(count.incrementAndGet());

        // Create the RegistroInspecciones
        RegistroInspeccionesDTO registroInspeccionesDTO = registroInspeccionesMapper.toDto(registroInspecciones);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegistroInspeccionesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(registroInspeccionesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RegistroInspecciones in the database
        List<RegistroInspecciones> registroInspeccionesList = registroInspeccionesRepository.findAll();
        assertThat(registroInspeccionesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRegistroInspecciones() throws Exception {
        // Initialize the database
        registroInspeccionesRepository.saveAndFlush(registroInspecciones);

        int databaseSizeBeforeDelete = registroInspeccionesRepository.findAll().size();

        // Delete the registroInspecciones
        restRegistroInspeccionesMockMvc
            .perform(delete(ENTITY_API_URL_ID, registroInspecciones.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RegistroInspecciones> registroInspeccionesList = registroInspeccionesRepository.findAll();
        assertThat(registroInspeccionesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
