package com.municipio.sistemasadm.web.rest;

import static com.municipio.sistemasadm.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.municipio.sistemasadm.IntegrationTest;
import com.municipio.sistemasadm.domain.Autorizaciones;
import com.municipio.sistemasadm.domain.enumeration.ContactoTecnico;
import com.municipio.sistemasadm.repository.AutorizacionesRepository;
import com.municipio.sistemasadm.service.dto.AutorizacionesDTO;
import com.municipio.sistemasadm.service.mapper.AutorizacionesMapper;
import java.time.Instant;
import java.time.LocalDate;
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
 * Integration tests for the {@link AutorizacionesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AutorizacionesResourceIT {

    private static final String DEFAULT_CLIENTE = "AAAAAAAAAA";
    private static final String UPDATED_CLIENTE = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION_ORIGEN = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION_ORIGEN = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_OPERACION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_OPERACION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_VENTANA_TRABAJO = "AAAAAAAAAA";
    private static final String UPDATED_VENTANA_TRABAJO = "BBBBBBBBBB";

    private static final ContactoTecnico DEFAULT_CONTACTO_TECNICO = ContactoTecnico.InSitu;
    private static final ContactoTecnico UPDATED_CONTACTO_TECNICO = ContactoTecnico.InSitu;

    private static final String DEFAULT_TIPO_TRABAJO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_TRABAJO = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACIONES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACIONES = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_DIRECCION_DESTINO = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION_DESTINO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/autorizaciones";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AutorizacionesRepository autorizacionesRepository;

    @Autowired
    private AutorizacionesMapper autorizacionesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAutorizacionesMockMvc;

    private Autorizaciones autorizaciones;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Autorizaciones createEntity(EntityManager em) {
        Autorizaciones autorizaciones = new Autorizaciones()
            .cliente(DEFAULT_CLIENTE)
            .direccionOrigen(DEFAULT_DIRECCION_ORIGEN)
            .fechaOperacion(DEFAULT_FECHA_OPERACION)
            .ventanaTrabajo(DEFAULT_VENTANA_TRABAJO)
            .contactoTecnico(DEFAULT_CONTACTO_TECNICO)
            .tipoTrabajo(DEFAULT_TIPO_TRABAJO)
            .observaciones(DEFAULT_OBSERVACIONES)
            .createdAt(DEFAULT_CREATED_AT)
            .direccionDestino(DEFAULT_DIRECCION_DESTINO);
        return autorizaciones;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Autorizaciones createUpdatedEntity(EntityManager em) {
        Autorizaciones autorizaciones = new Autorizaciones()
            .cliente(UPDATED_CLIENTE)
            .direccionOrigen(UPDATED_DIRECCION_ORIGEN)
            .fechaOperacion(UPDATED_FECHA_OPERACION)
            .ventanaTrabajo(UPDATED_VENTANA_TRABAJO)
            .contactoTecnico(UPDATED_CONTACTO_TECNICO)
            .tipoTrabajo(UPDATED_TIPO_TRABAJO)
            .observaciones(UPDATED_OBSERVACIONES)
            .createdAt(UPDATED_CREATED_AT)
            .direccionDestino(UPDATED_DIRECCION_DESTINO);
        return autorizaciones;
    }

    @BeforeEach
    public void initTest() {
        autorizaciones = createEntity(em);
    }

    @Test
    @Transactional
    void createAutorizaciones() throws Exception {
        int databaseSizeBeforeCreate = autorizacionesRepository.findAll().size();
        // Create the Autorizaciones
        AutorizacionesDTO autorizacionesDTO = autorizacionesMapper.toDto(autorizaciones);
        restAutorizacionesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(autorizacionesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Autorizaciones in the database
        List<Autorizaciones> autorizacionesList = autorizacionesRepository.findAll();
        assertThat(autorizacionesList).hasSize(databaseSizeBeforeCreate + 1);
        Autorizaciones testAutorizaciones = autorizacionesList.get(autorizacionesList.size() - 1);
        assertThat(testAutorizaciones.getCliente()).isEqualTo(DEFAULT_CLIENTE);
        assertThat(testAutorizaciones.getDireccionOrigen()).isEqualTo(DEFAULT_DIRECCION_ORIGEN);
        assertThat(testAutorizaciones.getFechaOperacion()).isEqualTo(DEFAULT_FECHA_OPERACION);
        assertThat(testAutorizaciones.getVentanaTrabajo()).isEqualTo(DEFAULT_VENTANA_TRABAJO);
        assertThat(testAutorizaciones.getContactoTecnico()).isEqualTo(DEFAULT_CONTACTO_TECNICO);
        assertThat(testAutorizaciones.getTipoTrabajo()).isEqualTo(DEFAULT_TIPO_TRABAJO);
        assertThat(testAutorizaciones.getObservaciones()).isEqualTo(DEFAULT_OBSERVACIONES);
        assertThat(testAutorizaciones.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testAutorizaciones.getDireccionDestino()).isEqualTo(DEFAULT_DIRECCION_DESTINO);
    }

    @Test
    @Transactional
    void createAutorizacionesWithExistingId() throws Exception {
        // Create the Autorizaciones with an existing ID
        autorizaciones.setId(1L);
        AutorizacionesDTO autorizacionesDTO = autorizacionesMapper.toDto(autorizaciones);

        int databaseSizeBeforeCreate = autorizacionesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAutorizacionesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(autorizacionesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autorizaciones in the database
        List<Autorizaciones> autorizacionesList = autorizacionesRepository.findAll();
        assertThat(autorizacionesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkClienteIsRequired() throws Exception {
        int databaseSizeBeforeTest = autorizacionesRepository.findAll().size();
        // set the field null
        autorizaciones.setCliente(null);

        // Create the Autorizaciones, which fails.
        AutorizacionesDTO autorizacionesDTO = autorizacionesMapper.toDto(autorizaciones);

        restAutorizacionesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(autorizacionesDTO))
            )
            .andExpect(status().isBadRequest());

        List<Autorizaciones> autorizacionesList = autorizacionesRepository.findAll();
        assertThat(autorizacionesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDireccionOrigenIsRequired() throws Exception {
        int databaseSizeBeforeTest = autorizacionesRepository.findAll().size();
        // set the field null
        autorizaciones.setDireccionOrigen(null);

        // Create the Autorizaciones, which fails.
        AutorizacionesDTO autorizacionesDTO = autorizacionesMapper.toDto(autorizaciones);

        restAutorizacionesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(autorizacionesDTO))
            )
            .andExpect(status().isBadRequest());

        List<Autorizaciones> autorizacionesList = autorizacionesRepository.findAll();
        assertThat(autorizacionesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaOperacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = autorizacionesRepository.findAll().size();
        // set the field null
        autorizaciones.setFechaOperacion(null);

        // Create the Autorizaciones, which fails.
        AutorizacionesDTO autorizacionesDTO = autorizacionesMapper.toDto(autorizaciones);

        restAutorizacionesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(autorizacionesDTO))
            )
            .andExpect(status().isBadRequest());

        List<Autorizaciones> autorizacionesList = autorizacionesRepository.findAll();
        assertThat(autorizacionesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVentanaTrabajoIsRequired() throws Exception {
        int databaseSizeBeforeTest = autorizacionesRepository.findAll().size();
        // set the field null
        autorizaciones.setVentanaTrabajo(null);

        // Create the Autorizaciones, which fails.
        AutorizacionesDTO autorizacionesDTO = autorizacionesMapper.toDto(autorizaciones);

        restAutorizacionesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(autorizacionesDTO))
            )
            .andExpect(status().isBadRequest());

        List<Autorizaciones> autorizacionesList = autorizacionesRepository.findAll();
        assertThat(autorizacionesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTipoTrabajoIsRequired() throws Exception {
        int databaseSizeBeforeTest = autorizacionesRepository.findAll().size();
        // set the field null
        autorizaciones.setTipoTrabajo(null);

        // Create the Autorizaciones, which fails.
        AutorizacionesDTO autorizacionesDTO = autorizacionesMapper.toDto(autorizaciones);

        restAutorizacionesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(autorizacionesDTO))
            )
            .andExpect(status().isBadRequest());

        List<Autorizaciones> autorizacionesList = autorizacionesRepository.findAll();
        assertThat(autorizacionesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkObservacionesIsRequired() throws Exception {
        int databaseSizeBeforeTest = autorizacionesRepository.findAll().size();
        // set the field null
        autorizaciones.setObservaciones(null);

        // Create the Autorizaciones, which fails.
        AutorizacionesDTO autorizacionesDTO = autorizacionesMapper.toDto(autorizaciones);

        restAutorizacionesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(autorizacionesDTO))
            )
            .andExpect(status().isBadRequest());

        List<Autorizaciones> autorizacionesList = autorizacionesRepository.findAll();
        assertThat(autorizacionesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = autorizacionesRepository.findAll().size();
        // set the field null
        autorizaciones.setCreatedAt(null);

        // Create the Autorizaciones, which fails.
        AutorizacionesDTO autorizacionesDTO = autorizacionesMapper.toDto(autorizaciones);

        restAutorizacionesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(autorizacionesDTO))
            )
            .andExpect(status().isBadRequest());

        List<Autorizaciones> autorizacionesList = autorizacionesRepository.findAll();
        assertThat(autorizacionesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDireccionDestinoIsRequired() throws Exception {
        int databaseSizeBeforeTest = autorizacionesRepository.findAll().size();
        // set the field null
        autorizaciones.setDireccionDestino(null);

        // Create the Autorizaciones, which fails.
        AutorizacionesDTO autorizacionesDTO = autorizacionesMapper.toDto(autorizaciones);

        restAutorizacionesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(autorizacionesDTO))
            )
            .andExpect(status().isBadRequest());

        List<Autorizaciones> autorizacionesList = autorizacionesRepository.findAll();
        assertThat(autorizacionesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAutorizaciones() throws Exception {
        // Initialize the database
        autorizacionesRepository.saveAndFlush(autorizaciones);

        // Get all the autorizacionesList
        restAutorizacionesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(autorizaciones.getId().intValue())))
            .andExpect(jsonPath("$.[*].cliente").value(hasItem(DEFAULT_CLIENTE)))
            .andExpect(jsonPath("$.[*].direccionOrigen").value(hasItem(DEFAULT_DIRECCION_ORIGEN)))
            .andExpect(jsonPath("$.[*].fechaOperacion").value(hasItem(DEFAULT_FECHA_OPERACION.toString())))
            .andExpect(jsonPath("$.[*].ventanaTrabajo").value(hasItem(DEFAULT_VENTANA_TRABAJO)))
            .andExpect(jsonPath("$.[*].contactoTecnico").value(hasItem(DEFAULT_CONTACTO_TECNICO.toString())))
            .andExpect(jsonPath("$.[*].tipoTrabajo").value(hasItem(DEFAULT_TIPO_TRABAJO)))
            .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].direccionDestino").value(hasItem(DEFAULT_DIRECCION_DESTINO)));
    }

    @Test
    @Transactional
    void getAutorizaciones() throws Exception {
        // Initialize the database
        autorizacionesRepository.saveAndFlush(autorizaciones);

        // Get the autorizaciones
        restAutorizacionesMockMvc
            .perform(get(ENTITY_API_URL_ID, autorizaciones.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(autorizaciones.getId().intValue()))
            .andExpect(jsonPath("$.cliente").value(DEFAULT_CLIENTE))
            .andExpect(jsonPath("$.direccionOrigen").value(DEFAULT_DIRECCION_ORIGEN))
            .andExpect(jsonPath("$.fechaOperacion").value(DEFAULT_FECHA_OPERACION.toString()))
            .andExpect(jsonPath("$.ventanaTrabajo").value(DEFAULT_VENTANA_TRABAJO))
            .andExpect(jsonPath("$.contactoTecnico").value(DEFAULT_CONTACTO_TECNICO.toString()))
            .andExpect(jsonPath("$.tipoTrabajo").value(DEFAULT_TIPO_TRABAJO))
            .andExpect(jsonPath("$.observaciones").value(DEFAULT_OBSERVACIONES))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.direccionDestino").value(DEFAULT_DIRECCION_DESTINO));
    }

    @Test
    @Transactional
    void getNonExistingAutorizaciones() throws Exception {
        // Get the autorizaciones
        restAutorizacionesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAutorizaciones() throws Exception {
        // Initialize the database
        autorizacionesRepository.saveAndFlush(autorizaciones);

        int databaseSizeBeforeUpdate = autorizacionesRepository.findAll().size();

        // Update the autorizaciones
        Autorizaciones updatedAutorizaciones = autorizacionesRepository.findById(autorizaciones.getId()).get();
        // Disconnect from session so that the updates on updatedAutorizaciones are not directly saved in db
        em.detach(updatedAutorizaciones);
        updatedAutorizaciones
            .cliente(UPDATED_CLIENTE)
            .direccionOrigen(UPDATED_DIRECCION_ORIGEN)
            .fechaOperacion(UPDATED_FECHA_OPERACION)
            .ventanaTrabajo(UPDATED_VENTANA_TRABAJO)
            .contactoTecnico(UPDATED_CONTACTO_TECNICO)
            .tipoTrabajo(UPDATED_TIPO_TRABAJO)
            .observaciones(UPDATED_OBSERVACIONES)
            .createdAt(UPDATED_CREATED_AT)
            .direccionDestino(UPDATED_DIRECCION_DESTINO);
        AutorizacionesDTO autorizacionesDTO = autorizacionesMapper.toDto(updatedAutorizaciones);

        restAutorizacionesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, autorizacionesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(autorizacionesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Autorizaciones in the database
        List<Autorizaciones> autorizacionesList = autorizacionesRepository.findAll();
        assertThat(autorizacionesList).hasSize(databaseSizeBeforeUpdate);
        Autorizaciones testAutorizaciones = autorizacionesList.get(autorizacionesList.size() - 1);
        assertThat(testAutorizaciones.getCliente()).isEqualTo(UPDATED_CLIENTE);
        assertThat(testAutorizaciones.getDireccionOrigen()).isEqualTo(UPDATED_DIRECCION_ORIGEN);
        assertThat(testAutorizaciones.getFechaOperacion()).isEqualTo(UPDATED_FECHA_OPERACION);
        assertThat(testAutorizaciones.getVentanaTrabajo()).isEqualTo(UPDATED_VENTANA_TRABAJO);
        assertThat(testAutorizaciones.getContactoTecnico()).isEqualTo(UPDATED_CONTACTO_TECNICO);
        assertThat(testAutorizaciones.getTipoTrabajo()).isEqualTo(UPDATED_TIPO_TRABAJO);
        assertThat(testAutorizaciones.getObservaciones()).isEqualTo(UPDATED_OBSERVACIONES);
        assertThat(testAutorizaciones.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testAutorizaciones.getDireccionDestino()).isEqualTo(UPDATED_DIRECCION_DESTINO);
    }

    @Test
    @Transactional
    void putNonExistingAutorizaciones() throws Exception {
        int databaseSizeBeforeUpdate = autorizacionesRepository.findAll().size();
        autorizaciones.setId(count.incrementAndGet());

        // Create the Autorizaciones
        AutorizacionesDTO autorizacionesDTO = autorizacionesMapper.toDto(autorizaciones);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutorizacionesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, autorizacionesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(autorizacionesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autorizaciones in the database
        List<Autorizaciones> autorizacionesList = autorizacionesRepository.findAll();
        assertThat(autorizacionesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAutorizaciones() throws Exception {
        int databaseSizeBeforeUpdate = autorizacionesRepository.findAll().size();
        autorizaciones.setId(count.incrementAndGet());

        // Create the Autorizaciones
        AutorizacionesDTO autorizacionesDTO = autorizacionesMapper.toDto(autorizaciones);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutorizacionesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(autorizacionesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autorizaciones in the database
        List<Autorizaciones> autorizacionesList = autorizacionesRepository.findAll();
        assertThat(autorizacionesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAutorizaciones() throws Exception {
        int databaseSizeBeforeUpdate = autorizacionesRepository.findAll().size();
        autorizaciones.setId(count.incrementAndGet());

        // Create the Autorizaciones
        AutorizacionesDTO autorizacionesDTO = autorizacionesMapper.toDto(autorizaciones);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutorizacionesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(autorizacionesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Autorizaciones in the database
        List<Autorizaciones> autorizacionesList = autorizacionesRepository.findAll();
        assertThat(autorizacionesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAutorizacionesWithPatch() throws Exception {
        // Initialize the database
        autorizacionesRepository.saveAndFlush(autorizaciones);

        int databaseSizeBeforeUpdate = autorizacionesRepository.findAll().size();

        // Update the autorizaciones using partial update
        Autorizaciones partialUpdatedAutorizaciones = new Autorizaciones();
        partialUpdatedAutorizaciones.setId(autorizaciones.getId());

        partialUpdatedAutorizaciones.cliente(UPDATED_CLIENTE).direccionOrigen(UPDATED_DIRECCION_ORIGEN);

        restAutorizacionesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAutorizaciones.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAutorizaciones))
            )
            .andExpect(status().isOk());

        // Validate the Autorizaciones in the database
        List<Autorizaciones> autorizacionesList = autorizacionesRepository.findAll();
        assertThat(autorizacionesList).hasSize(databaseSizeBeforeUpdate);
        Autorizaciones testAutorizaciones = autorizacionesList.get(autorizacionesList.size() - 1);
        assertThat(testAutorizaciones.getCliente()).isEqualTo(UPDATED_CLIENTE);
        assertThat(testAutorizaciones.getDireccionOrigen()).isEqualTo(UPDATED_DIRECCION_ORIGEN);
        assertThat(testAutorizaciones.getFechaOperacion()).isEqualTo(DEFAULT_FECHA_OPERACION);
        assertThat(testAutorizaciones.getVentanaTrabajo()).isEqualTo(DEFAULT_VENTANA_TRABAJO);
        assertThat(testAutorizaciones.getContactoTecnico()).isEqualTo(DEFAULT_CONTACTO_TECNICO);
        assertThat(testAutorizaciones.getTipoTrabajo()).isEqualTo(DEFAULT_TIPO_TRABAJO);
        assertThat(testAutorizaciones.getObservaciones()).isEqualTo(DEFAULT_OBSERVACIONES);
        assertThat(testAutorizaciones.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testAutorizaciones.getDireccionDestino()).isEqualTo(DEFAULT_DIRECCION_DESTINO);
    }

    @Test
    @Transactional
    void fullUpdateAutorizacionesWithPatch() throws Exception {
        // Initialize the database
        autorizacionesRepository.saveAndFlush(autorizaciones);

        int databaseSizeBeforeUpdate = autorizacionesRepository.findAll().size();

        // Update the autorizaciones using partial update
        Autorizaciones partialUpdatedAutorizaciones = new Autorizaciones();
        partialUpdatedAutorizaciones.setId(autorizaciones.getId());

        partialUpdatedAutorizaciones
            .cliente(UPDATED_CLIENTE)
            .direccionOrigen(UPDATED_DIRECCION_ORIGEN)
            .fechaOperacion(UPDATED_FECHA_OPERACION)
            .ventanaTrabajo(UPDATED_VENTANA_TRABAJO)
            .contactoTecnico(UPDATED_CONTACTO_TECNICO)
            .tipoTrabajo(UPDATED_TIPO_TRABAJO)
            .observaciones(UPDATED_OBSERVACIONES)
            .createdAt(UPDATED_CREATED_AT)
            .direccionDestino(UPDATED_DIRECCION_DESTINO);

        restAutorizacionesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAutorizaciones.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAutorizaciones))
            )
            .andExpect(status().isOk());

        // Validate the Autorizaciones in the database
        List<Autorizaciones> autorizacionesList = autorizacionesRepository.findAll();
        assertThat(autorizacionesList).hasSize(databaseSizeBeforeUpdate);
        Autorizaciones testAutorizaciones = autorizacionesList.get(autorizacionesList.size() - 1);
        assertThat(testAutorizaciones.getCliente()).isEqualTo(UPDATED_CLIENTE);
        assertThat(testAutorizaciones.getDireccionOrigen()).isEqualTo(UPDATED_DIRECCION_ORIGEN);
        assertThat(testAutorizaciones.getFechaOperacion()).isEqualTo(UPDATED_FECHA_OPERACION);
        assertThat(testAutorizaciones.getVentanaTrabajo()).isEqualTo(UPDATED_VENTANA_TRABAJO);
        assertThat(testAutorizaciones.getContactoTecnico()).isEqualTo(UPDATED_CONTACTO_TECNICO);
        assertThat(testAutorizaciones.getTipoTrabajo()).isEqualTo(UPDATED_TIPO_TRABAJO);
        assertThat(testAutorizaciones.getObservaciones()).isEqualTo(UPDATED_OBSERVACIONES);
        assertThat(testAutorizaciones.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testAutorizaciones.getDireccionDestino()).isEqualTo(UPDATED_DIRECCION_DESTINO);
    }

    @Test
    @Transactional
    void patchNonExistingAutorizaciones() throws Exception {
        int databaseSizeBeforeUpdate = autorizacionesRepository.findAll().size();
        autorizaciones.setId(count.incrementAndGet());

        // Create the Autorizaciones
        AutorizacionesDTO autorizacionesDTO = autorizacionesMapper.toDto(autorizaciones);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAutorizacionesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, autorizacionesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(autorizacionesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autorizaciones in the database
        List<Autorizaciones> autorizacionesList = autorizacionesRepository.findAll();
        assertThat(autorizacionesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAutorizaciones() throws Exception {
        int databaseSizeBeforeUpdate = autorizacionesRepository.findAll().size();
        autorizaciones.setId(count.incrementAndGet());

        // Create the Autorizaciones
        AutorizacionesDTO autorizacionesDTO = autorizacionesMapper.toDto(autorizaciones);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutorizacionesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(autorizacionesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Autorizaciones in the database
        List<Autorizaciones> autorizacionesList = autorizacionesRepository.findAll();
        assertThat(autorizacionesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAutorizaciones() throws Exception {
        int databaseSizeBeforeUpdate = autorizacionesRepository.findAll().size();
        autorizaciones.setId(count.incrementAndGet());

        // Create the Autorizaciones
        AutorizacionesDTO autorizacionesDTO = autorizacionesMapper.toDto(autorizaciones);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAutorizacionesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(autorizacionesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Autorizaciones in the database
        List<Autorizaciones> autorizacionesList = autorizacionesRepository.findAll();
        assertThat(autorizacionesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAutorizaciones() throws Exception {
        // Initialize the database
        autorizacionesRepository.saveAndFlush(autorizaciones);

        int databaseSizeBeforeDelete = autorizacionesRepository.findAll().size();

        // Delete the autorizaciones
        restAutorizacionesMockMvc
            .perform(delete(ENTITY_API_URL_ID, autorizaciones.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Autorizaciones> autorizacionesList = autorizacionesRepository.findAll();
        assertThat(autorizacionesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
