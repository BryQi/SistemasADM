package com.municipio.sistemasadm.web.rest;

import static com.municipio.sistemasadm.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.municipio.sistemasadm.IntegrationTest;
import com.municipio.sistemasadm.domain.DespliegueInfraestructuraTroncalDistribucion;
import com.municipio.sistemasadm.domain.Pozo;
import com.municipio.sistemasadm.domain.Proveedor;
import com.municipio.sistemasadm.repository.DespliegueInfraestructuraTroncalDistribucionRepository;
import com.municipio.sistemasadm.service.DespliegueInfraestructuraTroncalDistribucionService;
import com.municipio.sistemasadm.service.dto.DespliegueInfraestructuraTroncalDistribucionDTO;
import com.municipio.sistemasadm.service.mapper.DespliegueInfraestructuraTroncalDistribucionMapper;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DespliegueInfraestructuraTroncalDistribucionResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DespliegueInfraestructuraTroncalDistribucionResourceIT {

    private static final String DEFAULT_NOMBRE_RUTA = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_RUTA = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION_RUTA = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION_RUTA = "BBBBBBBBBB";

    private static final Double DEFAULT_METRAJE_INICIAL = 1D;
    private static final Double UPDATED_METRAJE_INICIAL = 2D;

    private static final Double DEFAULT_METRAJE_FINAL = 1D;
    private static final Double UPDATED_METRAJE_FINAL = 2D;

    private static final Double DEFAULT_CALCULO_VALOR_PAGO = 1D;
    private static final Double UPDATED_CALCULO_VALOR_PAGO = 2D;

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Float DEFAULT_VALOR_METRO = 1F;
    private static final Float UPDATED_VALOR_METRO = 2F;

    private static final String ENTITY_API_URL = "/api/despliegue-infraestructura-troncal-distribucions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DespliegueInfraestructuraTroncalDistribucionRepository despliegueInfraestructuraTroncalDistribucionRepository;

    @Mock
    private DespliegueInfraestructuraTroncalDistribucionRepository despliegueInfraestructuraTroncalDistribucionRepositoryMock;

    @Autowired
    private DespliegueInfraestructuraTroncalDistribucionMapper despliegueInfraestructuraTroncalDistribucionMapper;

    @Mock
    private DespliegueInfraestructuraTroncalDistribucionService despliegueInfraestructuraTroncalDistribucionServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDespliegueInfraestructuraTroncalDistribucionMockMvc;

    private DespliegueInfraestructuraTroncalDistribucion despliegueInfraestructuraTroncalDistribucion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DespliegueInfraestructuraTroncalDistribucion createEntity(EntityManager em) {
        DespliegueInfraestructuraTroncalDistribucion despliegueInfraestructuraTroncalDistribucion = new DespliegueInfraestructuraTroncalDistribucion()
            .nombreRuta(DEFAULT_NOMBRE_RUTA)
            .descripcionRuta(DEFAULT_DESCRIPCION_RUTA)
            .metrajeInicial(DEFAULT_METRAJE_INICIAL)
            .metrajeFinal(DEFAULT_METRAJE_FINAL)
            .calculoValorPago(DEFAULT_CALCULO_VALOR_PAGO)
            .createdAt(DEFAULT_CREATED_AT)
            .valorMetro(DEFAULT_VALOR_METRO);
        // Add required entity
        Pozo pozo;
        if (TestUtil.findAll(em, Pozo.class).isEmpty()) {
            pozo = PozoResourceIT.createEntity(em);
            em.persist(pozo);
            em.flush();
        } else {
            pozo = TestUtil.findAll(em, Pozo.class).get(0);
        }
        despliegueInfraestructuraTroncalDistribucion.getPozos().add(pozo);
        // Add required entity
        Proveedor proveedor;
        if (TestUtil.findAll(em, Proveedor.class).isEmpty()) {
            proveedor = ProveedorResourceIT.createEntity(em);
            em.persist(proveedor);
            em.flush();
        } else {
            proveedor = TestUtil.findAll(em, Proveedor.class).get(0);
        }
        despliegueInfraestructuraTroncalDistribucion.setRazonSocial(proveedor);
        return despliegueInfraestructuraTroncalDistribucion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DespliegueInfraestructuraTroncalDistribucion createUpdatedEntity(EntityManager em) {
        DespliegueInfraestructuraTroncalDistribucion despliegueInfraestructuraTroncalDistribucion = new DespliegueInfraestructuraTroncalDistribucion()
            .nombreRuta(UPDATED_NOMBRE_RUTA)
            .descripcionRuta(UPDATED_DESCRIPCION_RUTA)
            .metrajeInicial(UPDATED_METRAJE_INICIAL)
            .metrajeFinal(UPDATED_METRAJE_FINAL)
            .calculoValorPago(UPDATED_CALCULO_VALOR_PAGO)
            .createdAt(UPDATED_CREATED_AT)
            .valorMetro(UPDATED_VALOR_METRO);
        // Add required entity
        Pozo pozo;
        if (TestUtil.findAll(em, Pozo.class).isEmpty()) {
            pozo = PozoResourceIT.createUpdatedEntity(em);
            em.persist(pozo);
            em.flush();
        } else {
            pozo = TestUtil.findAll(em, Pozo.class).get(0);
        }
        despliegueInfraestructuraTroncalDistribucion.getPozos().add(pozo);
        // Add required entity
        Proveedor proveedor;
        if (TestUtil.findAll(em, Proveedor.class).isEmpty()) {
            proveedor = ProveedorResourceIT.createUpdatedEntity(em);
            em.persist(proveedor);
            em.flush();
        } else {
            proveedor = TestUtil.findAll(em, Proveedor.class).get(0);
        }
        despliegueInfraestructuraTroncalDistribucion.setRazonSocial(proveedor);
        return despliegueInfraestructuraTroncalDistribucion;
    }

    @BeforeEach
    public void initTest() {
        despliegueInfraestructuraTroncalDistribucion = createEntity(em);
    }

    @Test
    @Transactional
    void createDespliegueInfraestructuraTroncalDistribucion() throws Exception {
        int databaseSizeBeforeCreate = despliegueInfraestructuraTroncalDistribucionRepository.findAll().size();
        // Create the DespliegueInfraestructuraTroncalDistribucion
        DespliegueInfraestructuraTroncalDistribucionDTO despliegueInfraestructuraTroncalDistribucionDTO = despliegueInfraestructuraTroncalDistribucionMapper.toDto(
            despliegueInfraestructuraTroncalDistribucion
        );
        restDespliegueInfraestructuraTroncalDistribucionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(despliegueInfraestructuraTroncalDistribucionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DespliegueInfraestructuraTroncalDistribucion in the database
        List<DespliegueInfraestructuraTroncalDistribucion> despliegueInfraestructuraTroncalDistribucionList = despliegueInfraestructuraTroncalDistribucionRepository.findAll();
        assertThat(despliegueInfraestructuraTroncalDistribucionList).hasSize(databaseSizeBeforeCreate + 1);
        DespliegueInfraestructuraTroncalDistribucion testDespliegueInfraestructuraTroncalDistribucion = despliegueInfraestructuraTroncalDistribucionList.get(
            despliegueInfraestructuraTroncalDistribucionList.size() - 1
        );
        assertThat(testDespliegueInfraestructuraTroncalDistribucion.getNombreRuta()).isEqualTo(DEFAULT_NOMBRE_RUTA);
        assertThat(testDespliegueInfraestructuraTroncalDistribucion.getDescripcionRuta()).isEqualTo(DEFAULT_DESCRIPCION_RUTA);
        assertThat(testDespliegueInfraestructuraTroncalDistribucion.getMetrajeInicial()).isEqualTo(DEFAULT_METRAJE_INICIAL);
        assertThat(testDespliegueInfraestructuraTroncalDistribucion.getMetrajeFinal()).isEqualTo(DEFAULT_METRAJE_FINAL);
        assertThat(testDespliegueInfraestructuraTroncalDistribucion.getCalculoValorPago()).isEqualTo(DEFAULT_CALCULO_VALOR_PAGO);
        assertThat(testDespliegueInfraestructuraTroncalDistribucion.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testDespliegueInfraestructuraTroncalDistribucion.getValorMetro()).isEqualTo(DEFAULT_VALOR_METRO);
    }

    @Test
    @Transactional
    void createDespliegueInfraestructuraTroncalDistribucionWithExistingId() throws Exception {
        // Create the DespliegueInfraestructuraTroncalDistribucion with an existing ID
        despliegueInfraestructuraTroncalDistribucion.setId(1L);
        DespliegueInfraestructuraTroncalDistribucionDTO despliegueInfraestructuraTroncalDistribucionDTO = despliegueInfraestructuraTroncalDistribucionMapper.toDto(
            despliegueInfraestructuraTroncalDistribucion
        );

        int databaseSizeBeforeCreate = despliegueInfraestructuraTroncalDistribucionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDespliegueInfraestructuraTroncalDistribucionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(despliegueInfraestructuraTroncalDistribucionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DespliegueInfraestructuraTroncalDistribucion in the database
        List<DespliegueInfraestructuraTroncalDistribucion> despliegueInfraestructuraTroncalDistribucionList = despliegueInfraestructuraTroncalDistribucionRepository.findAll();
        assertThat(despliegueInfraestructuraTroncalDistribucionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreRutaIsRequired() throws Exception {
        int databaseSizeBeforeTest = despliegueInfraestructuraTroncalDistribucionRepository.findAll().size();
        // set the field null
        despliegueInfraestructuraTroncalDistribucion.setNombreRuta(null);

        // Create the DespliegueInfraestructuraTroncalDistribucion, which fails.
        DespliegueInfraestructuraTroncalDistribucionDTO despliegueInfraestructuraTroncalDistribucionDTO = despliegueInfraestructuraTroncalDistribucionMapper.toDto(
            despliegueInfraestructuraTroncalDistribucion
        );

        restDespliegueInfraestructuraTroncalDistribucionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(despliegueInfraestructuraTroncalDistribucionDTO))
            )
            .andExpect(status().isBadRequest());

        List<DespliegueInfraestructuraTroncalDistribucion> despliegueInfraestructuraTroncalDistribucionList = despliegueInfraestructuraTroncalDistribucionRepository.findAll();
        assertThat(despliegueInfraestructuraTroncalDistribucionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescripcionRutaIsRequired() throws Exception {
        int databaseSizeBeforeTest = despliegueInfraestructuraTroncalDistribucionRepository.findAll().size();
        // set the field null
        despliegueInfraestructuraTroncalDistribucion.setDescripcionRuta(null);

        // Create the DespliegueInfraestructuraTroncalDistribucion, which fails.
        DespliegueInfraestructuraTroncalDistribucionDTO despliegueInfraestructuraTroncalDistribucionDTO = despliegueInfraestructuraTroncalDistribucionMapper.toDto(
            despliegueInfraestructuraTroncalDistribucion
        );

        restDespliegueInfraestructuraTroncalDistribucionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(despliegueInfraestructuraTroncalDistribucionDTO))
            )
            .andExpect(status().isBadRequest());

        List<DespliegueInfraestructuraTroncalDistribucion> despliegueInfraestructuraTroncalDistribucionList = despliegueInfraestructuraTroncalDistribucionRepository.findAll();
        assertThat(despliegueInfraestructuraTroncalDistribucionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMetrajeInicialIsRequired() throws Exception {
        int databaseSizeBeforeTest = despliegueInfraestructuraTroncalDistribucionRepository.findAll().size();
        // set the field null
        despliegueInfraestructuraTroncalDistribucion.setMetrajeInicial(null);

        // Create the DespliegueInfraestructuraTroncalDistribucion, which fails.
        DespliegueInfraestructuraTroncalDistribucionDTO despliegueInfraestructuraTroncalDistribucionDTO = despliegueInfraestructuraTroncalDistribucionMapper.toDto(
            despliegueInfraestructuraTroncalDistribucion
        );

        restDespliegueInfraestructuraTroncalDistribucionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(despliegueInfraestructuraTroncalDistribucionDTO))
            )
            .andExpect(status().isBadRequest());

        List<DespliegueInfraestructuraTroncalDistribucion> despliegueInfraestructuraTroncalDistribucionList = despliegueInfraestructuraTroncalDistribucionRepository.findAll();
        assertThat(despliegueInfraestructuraTroncalDistribucionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMetrajeFinalIsRequired() throws Exception {
        int databaseSizeBeforeTest = despliegueInfraestructuraTroncalDistribucionRepository.findAll().size();
        // set the field null
        despliegueInfraestructuraTroncalDistribucion.setMetrajeFinal(null);

        // Create the DespliegueInfraestructuraTroncalDistribucion, which fails.
        DespliegueInfraestructuraTroncalDistribucionDTO despliegueInfraestructuraTroncalDistribucionDTO = despliegueInfraestructuraTroncalDistribucionMapper.toDto(
            despliegueInfraestructuraTroncalDistribucion
        );

        restDespliegueInfraestructuraTroncalDistribucionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(despliegueInfraestructuraTroncalDistribucionDTO))
            )
            .andExpect(status().isBadRequest());

        List<DespliegueInfraestructuraTroncalDistribucion> despliegueInfraestructuraTroncalDistribucionList = despliegueInfraestructuraTroncalDistribucionRepository.findAll();
        assertThat(despliegueInfraestructuraTroncalDistribucionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCalculoValorPagoIsRequired() throws Exception {
        int databaseSizeBeforeTest = despliegueInfraestructuraTroncalDistribucionRepository.findAll().size();
        // set the field null
        despliegueInfraestructuraTroncalDistribucion.setCalculoValorPago(null);

        // Create the DespliegueInfraestructuraTroncalDistribucion, which fails.
        DespliegueInfraestructuraTroncalDistribucionDTO despliegueInfraestructuraTroncalDistribucionDTO = despliegueInfraestructuraTroncalDistribucionMapper.toDto(
            despliegueInfraestructuraTroncalDistribucion
        );

        restDespliegueInfraestructuraTroncalDistribucionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(despliegueInfraestructuraTroncalDistribucionDTO))
            )
            .andExpect(status().isBadRequest());

        List<DespliegueInfraestructuraTroncalDistribucion> despliegueInfraestructuraTroncalDistribucionList = despliegueInfraestructuraTroncalDistribucionRepository.findAll();
        assertThat(despliegueInfraestructuraTroncalDistribucionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = despliegueInfraestructuraTroncalDistribucionRepository.findAll().size();
        // set the field null
        despliegueInfraestructuraTroncalDistribucion.setCreatedAt(null);

        // Create the DespliegueInfraestructuraTroncalDistribucion, which fails.
        DespliegueInfraestructuraTroncalDistribucionDTO despliegueInfraestructuraTroncalDistribucionDTO = despliegueInfraestructuraTroncalDistribucionMapper.toDto(
            despliegueInfraestructuraTroncalDistribucion
        );

        restDespliegueInfraestructuraTroncalDistribucionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(despliegueInfraestructuraTroncalDistribucionDTO))
            )
            .andExpect(status().isBadRequest());

        List<DespliegueInfraestructuraTroncalDistribucion> despliegueInfraestructuraTroncalDistribucionList = despliegueInfraestructuraTroncalDistribucionRepository.findAll();
        assertThat(despliegueInfraestructuraTroncalDistribucionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValorMetroIsRequired() throws Exception {
        int databaseSizeBeforeTest = despliegueInfraestructuraTroncalDistribucionRepository.findAll().size();
        // set the field null
        despliegueInfraestructuraTroncalDistribucion.setValorMetro(null);

        // Create the DespliegueInfraestructuraTroncalDistribucion, which fails.
        DespliegueInfraestructuraTroncalDistribucionDTO despliegueInfraestructuraTroncalDistribucionDTO = despliegueInfraestructuraTroncalDistribucionMapper.toDto(
            despliegueInfraestructuraTroncalDistribucion
        );

        restDespliegueInfraestructuraTroncalDistribucionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(despliegueInfraestructuraTroncalDistribucionDTO))
            )
            .andExpect(status().isBadRequest());

        List<DespliegueInfraestructuraTroncalDistribucion> despliegueInfraestructuraTroncalDistribucionList = despliegueInfraestructuraTroncalDistribucionRepository.findAll();
        assertThat(despliegueInfraestructuraTroncalDistribucionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDespliegueInfraestructuraTroncalDistribucions() throws Exception {
        // Initialize the database
        despliegueInfraestructuraTroncalDistribucionRepository.saveAndFlush(despliegueInfraestructuraTroncalDistribucion);

        // Get all the despliegueInfraestructuraTroncalDistribucionList
        restDespliegueInfraestructuraTroncalDistribucionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(despliegueInfraestructuraTroncalDistribucion.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreRuta").value(hasItem(DEFAULT_NOMBRE_RUTA)))
            .andExpect(jsonPath("$.[*].descripcionRuta").value(hasItem(DEFAULT_DESCRIPCION_RUTA)))
            .andExpect(jsonPath("$.[*].metrajeInicial").value(hasItem(DEFAULT_METRAJE_INICIAL.doubleValue())))
            .andExpect(jsonPath("$.[*].metrajeFinal").value(hasItem(DEFAULT_METRAJE_FINAL.doubleValue())))
            .andExpect(jsonPath("$.[*].calculoValorPago").value(hasItem(DEFAULT_CALCULO_VALOR_PAGO.doubleValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].valorMetro").value(hasItem(DEFAULT_VALOR_METRO.doubleValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDespliegueInfraestructuraTroncalDistribucionsWithEagerRelationshipsIsEnabled() throws Exception {
        when(despliegueInfraestructuraTroncalDistribucionServiceMock.findAllWithEagerRelationships(any()))
            .thenReturn(new PageImpl(new ArrayList<>()));

        restDespliegueInfraestructuraTroncalDistribucionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(despliegueInfraestructuraTroncalDistribucionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDespliegueInfraestructuraTroncalDistribucionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(despliegueInfraestructuraTroncalDistribucionServiceMock.findAllWithEagerRelationships(any()))
            .thenReturn(new PageImpl(new ArrayList<>()));

        restDespliegueInfraestructuraTroncalDistribucionMockMvc
            .perform(get(ENTITY_API_URL + "?eagerload=false"))
            .andExpect(status().isOk());
        verify(despliegueInfraestructuraTroncalDistribucionRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDespliegueInfraestructuraTroncalDistribucion() throws Exception {
        // Initialize the database
        despliegueInfraestructuraTroncalDistribucionRepository.saveAndFlush(despliegueInfraestructuraTroncalDistribucion);

        // Get the despliegueInfraestructuraTroncalDistribucion
        restDespliegueInfraestructuraTroncalDistribucionMockMvc
            .perform(get(ENTITY_API_URL_ID, despliegueInfraestructuraTroncalDistribucion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(despliegueInfraestructuraTroncalDistribucion.getId().intValue()))
            .andExpect(jsonPath("$.nombreRuta").value(DEFAULT_NOMBRE_RUTA))
            .andExpect(jsonPath("$.descripcionRuta").value(DEFAULT_DESCRIPCION_RUTA))
            .andExpect(jsonPath("$.metrajeInicial").value(DEFAULT_METRAJE_INICIAL.doubleValue()))
            .andExpect(jsonPath("$.metrajeFinal").value(DEFAULT_METRAJE_FINAL.doubleValue()))
            .andExpect(jsonPath("$.calculoValorPago").value(DEFAULT_CALCULO_VALOR_PAGO.doubleValue()))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.valorMetro").value(DEFAULT_VALOR_METRO.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingDespliegueInfraestructuraTroncalDistribucion() throws Exception {
        // Get the despliegueInfraestructuraTroncalDistribucion
        restDespliegueInfraestructuraTroncalDistribucionMockMvc
            .perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDespliegueInfraestructuraTroncalDistribucion() throws Exception {
        // Initialize the database
        despliegueInfraestructuraTroncalDistribucionRepository.saveAndFlush(despliegueInfraestructuraTroncalDistribucion);

        int databaseSizeBeforeUpdate = despliegueInfraestructuraTroncalDistribucionRepository.findAll().size();

        // Update the despliegueInfraestructuraTroncalDistribucion
        DespliegueInfraestructuraTroncalDistribucion updatedDespliegueInfraestructuraTroncalDistribucion = despliegueInfraestructuraTroncalDistribucionRepository
            .findById(despliegueInfraestructuraTroncalDistribucion.getId())
            .get();
        // Disconnect from session so that the updates on updatedDespliegueInfraestructuraTroncalDistribucion are not directly saved in db
        em.detach(updatedDespliegueInfraestructuraTroncalDistribucion);
        updatedDespliegueInfraestructuraTroncalDistribucion
            .nombreRuta(UPDATED_NOMBRE_RUTA)
            .descripcionRuta(UPDATED_DESCRIPCION_RUTA)
            .metrajeInicial(UPDATED_METRAJE_INICIAL)
            .metrajeFinal(UPDATED_METRAJE_FINAL)
            .calculoValorPago(UPDATED_CALCULO_VALOR_PAGO)
            .createdAt(UPDATED_CREATED_AT)
            .valorMetro(UPDATED_VALOR_METRO);
        DespliegueInfraestructuraTroncalDistribucionDTO despliegueInfraestructuraTroncalDistribucionDTO = despliegueInfraestructuraTroncalDistribucionMapper.toDto(
            updatedDespliegueInfraestructuraTroncalDistribucion
        );

        restDespliegueInfraestructuraTroncalDistribucionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, despliegueInfraestructuraTroncalDistribucionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(despliegueInfraestructuraTroncalDistribucionDTO))
            )
            .andExpect(status().isOk());

        // Validate the DespliegueInfraestructuraTroncalDistribucion in the database
        List<DespliegueInfraestructuraTroncalDistribucion> despliegueInfraestructuraTroncalDistribucionList = despliegueInfraestructuraTroncalDistribucionRepository.findAll();
        assertThat(despliegueInfraestructuraTroncalDistribucionList).hasSize(databaseSizeBeforeUpdate);
        DespliegueInfraestructuraTroncalDistribucion testDespliegueInfraestructuraTroncalDistribucion = despliegueInfraestructuraTroncalDistribucionList.get(
            despliegueInfraestructuraTroncalDistribucionList.size() - 1
        );
        assertThat(testDespliegueInfraestructuraTroncalDistribucion.getNombreRuta()).isEqualTo(UPDATED_NOMBRE_RUTA);
        assertThat(testDespliegueInfraestructuraTroncalDistribucion.getDescripcionRuta()).isEqualTo(UPDATED_DESCRIPCION_RUTA);
        assertThat(testDespliegueInfraestructuraTroncalDistribucion.getMetrajeInicial()).isEqualTo(UPDATED_METRAJE_INICIAL);
        assertThat(testDespliegueInfraestructuraTroncalDistribucion.getMetrajeFinal()).isEqualTo(UPDATED_METRAJE_FINAL);
        assertThat(testDespliegueInfraestructuraTroncalDistribucion.getCalculoValorPago()).isEqualTo(UPDATED_CALCULO_VALOR_PAGO);
        assertThat(testDespliegueInfraestructuraTroncalDistribucion.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testDespliegueInfraestructuraTroncalDistribucion.getValorMetro()).isEqualTo(UPDATED_VALOR_METRO);
    }

    @Test
    @Transactional
    void putNonExistingDespliegueInfraestructuraTroncalDistribucion() throws Exception {
        int databaseSizeBeforeUpdate = despliegueInfraestructuraTroncalDistribucionRepository.findAll().size();
        despliegueInfraestructuraTroncalDistribucion.setId(count.incrementAndGet());

        // Create the DespliegueInfraestructuraTroncalDistribucion
        DespliegueInfraestructuraTroncalDistribucionDTO despliegueInfraestructuraTroncalDistribucionDTO = despliegueInfraestructuraTroncalDistribucionMapper.toDto(
            despliegueInfraestructuraTroncalDistribucion
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDespliegueInfraestructuraTroncalDistribucionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, despliegueInfraestructuraTroncalDistribucionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(despliegueInfraestructuraTroncalDistribucionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DespliegueInfraestructuraTroncalDistribucion in the database
        List<DespliegueInfraestructuraTroncalDistribucion> despliegueInfraestructuraTroncalDistribucionList = despliegueInfraestructuraTroncalDistribucionRepository.findAll();
        assertThat(despliegueInfraestructuraTroncalDistribucionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDespliegueInfraestructuraTroncalDistribucion() throws Exception {
        int databaseSizeBeforeUpdate = despliegueInfraestructuraTroncalDistribucionRepository.findAll().size();
        despliegueInfraestructuraTroncalDistribucion.setId(count.incrementAndGet());

        // Create the DespliegueInfraestructuraTroncalDistribucion
        DespliegueInfraestructuraTroncalDistribucionDTO despliegueInfraestructuraTroncalDistribucionDTO = despliegueInfraestructuraTroncalDistribucionMapper.toDto(
            despliegueInfraestructuraTroncalDistribucion
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDespliegueInfraestructuraTroncalDistribucionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(despliegueInfraestructuraTroncalDistribucionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DespliegueInfraestructuraTroncalDistribucion in the database
        List<DespliegueInfraestructuraTroncalDistribucion> despliegueInfraestructuraTroncalDistribucionList = despliegueInfraestructuraTroncalDistribucionRepository.findAll();
        assertThat(despliegueInfraestructuraTroncalDistribucionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDespliegueInfraestructuraTroncalDistribucion() throws Exception {
        int databaseSizeBeforeUpdate = despliegueInfraestructuraTroncalDistribucionRepository.findAll().size();
        despliegueInfraestructuraTroncalDistribucion.setId(count.incrementAndGet());

        // Create the DespliegueInfraestructuraTroncalDistribucion
        DespliegueInfraestructuraTroncalDistribucionDTO despliegueInfraestructuraTroncalDistribucionDTO = despliegueInfraestructuraTroncalDistribucionMapper.toDto(
            despliegueInfraestructuraTroncalDistribucion
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDespliegueInfraestructuraTroncalDistribucionMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(despliegueInfraestructuraTroncalDistribucionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DespliegueInfraestructuraTroncalDistribucion in the database
        List<DespliegueInfraestructuraTroncalDistribucion> despliegueInfraestructuraTroncalDistribucionList = despliegueInfraestructuraTroncalDistribucionRepository.findAll();
        assertThat(despliegueInfraestructuraTroncalDistribucionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDespliegueInfraestructuraTroncalDistribucionWithPatch() throws Exception {
        // Initialize the database
        despliegueInfraestructuraTroncalDistribucionRepository.saveAndFlush(despliegueInfraestructuraTroncalDistribucion);

        int databaseSizeBeforeUpdate = despliegueInfraestructuraTroncalDistribucionRepository.findAll().size();

        // Update the despliegueInfraestructuraTroncalDistribucion using partial update
        DespliegueInfraestructuraTroncalDistribucion partialUpdatedDespliegueInfraestructuraTroncalDistribucion = new DespliegueInfraestructuraTroncalDistribucion();
        partialUpdatedDespliegueInfraestructuraTroncalDistribucion.setId(despliegueInfraestructuraTroncalDistribucion.getId());

        partialUpdatedDespliegueInfraestructuraTroncalDistribucion
            .nombreRuta(UPDATED_NOMBRE_RUTA)
            .descripcionRuta(UPDATED_DESCRIPCION_RUTA)
            .metrajeInicial(UPDATED_METRAJE_INICIAL)
            .metrajeFinal(UPDATED_METRAJE_FINAL)
            .calculoValorPago(UPDATED_CALCULO_VALOR_PAGO);

        restDespliegueInfraestructuraTroncalDistribucionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDespliegueInfraestructuraTroncalDistribucion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDespliegueInfraestructuraTroncalDistribucion))
            )
            .andExpect(status().isOk());

        // Validate the DespliegueInfraestructuraTroncalDistribucion in the database
        List<DespliegueInfraestructuraTroncalDistribucion> despliegueInfraestructuraTroncalDistribucionList = despliegueInfraestructuraTroncalDistribucionRepository.findAll();
        assertThat(despliegueInfraestructuraTroncalDistribucionList).hasSize(databaseSizeBeforeUpdate);
        DespliegueInfraestructuraTroncalDistribucion testDespliegueInfraestructuraTroncalDistribucion = despliegueInfraestructuraTroncalDistribucionList.get(
            despliegueInfraestructuraTroncalDistribucionList.size() - 1
        );
        assertThat(testDespliegueInfraestructuraTroncalDistribucion.getNombreRuta()).isEqualTo(UPDATED_NOMBRE_RUTA);
        assertThat(testDespliegueInfraestructuraTroncalDistribucion.getDescripcionRuta()).isEqualTo(UPDATED_DESCRIPCION_RUTA);
        assertThat(testDespliegueInfraestructuraTroncalDistribucion.getMetrajeInicial()).isEqualTo(UPDATED_METRAJE_INICIAL);
        assertThat(testDespliegueInfraestructuraTroncalDistribucion.getMetrajeFinal()).isEqualTo(UPDATED_METRAJE_FINAL);
        assertThat(testDespliegueInfraestructuraTroncalDistribucion.getCalculoValorPago()).isEqualTo(UPDATED_CALCULO_VALOR_PAGO);
        assertThat(testDespliegueInfraestructuraTroncalDistribucion.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testDespliegueInfraestructuraTroncalDistribucion.getValorMetro()).isEqualTo(DEFAULT_VALOR_METRO);
    }

    @Test
    @Transactional
    void fullUpdateDespliegueInfraestructuraTroncalDistribucionWithPatch() throws Exception {
        // Initialize the database
        despliegueInfraestructuraTroncalDistribucionRepository.saveAndFlush(despliegueInfraestructuraTroncalDistribucion);

        int databaseSizeBeforeUpdate = despliegueInfraestructuraTroncalDistribucionRepository.findAll().size();

        // Update the despliegueInfraestructuraTroncalDistribucion using partial update
        DespliegueInfraestructuraTroncalDistribucion partialUpdatedDespliegueInfraestructuraTroncalDistribucion = new DespliegueInfraestructuraTroncalDistribucion();
        partialUpdatedDespliegueInfraestructuraTroncalDistribucion.setId(despliegueInfraestructuraTroncalDistribucion.getId());

        partialUpdatedDespliegueInfraestructuraTroncalDistribucion
            .nombreRuta(UPDATED_NOMBRE_RUTA)
            .descripcionRuta(UPDATED_DESCRIPCION_RUTA)
            .metrajeInicial(UPDATED_METRAJE_INICIAL)
            .metrajeFinal(UPDATED_METRAJE_FINAL)
            .calculoValorPago(UPDATED_CALCULO_VALOR_PAGO)
            .createdAt(UPDATED_CREATED_AT)
            .valorMetro(UPDATED_VALOR_METRO);

        restDespliegueInfraestructuraTroncalDistribucionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDespliegueInfraestructuraTroncalDistribucion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDespliegueInfraestructuraTroncalDistribucion))
            )
            .andExpect(status().isOk());

        // Validate the DespliegueInfraestructuraTroncalDistribucion in the database
        List<DespliegueInfraestructuraTroncalDistribucion> despliegueInfraestructuraTroncalDistribucionList = despliegueInfraestructuraTroncalDistribucionRepository.findAll();
        assertThat(despliegueInfraestructuraTroncalDistribucionList).hasSize(databaseSizeBeforeUpdate);
        DespliegueInfraestructuraTroncalDistribucion testDespliegueInfraestructuraTroncalDistribucion = despliegueInfraestructuraTroncalDistribucionList.get(
            despliegueInfraestructuraTroncalDistribucionList.size() - 1
        );
        assertThat(testDespliegueInfraestructuraTroncalDistribucion.getNombreRuta()).isEqualTo(UPDATED_NOMBRE_RUTA);
        assertThat(testDespliegueInfraestructuraTroncalDistribucion.getDescripcionRuta()).isEqualTo(UPDATED_DESCRIPCION_RUTA);
        assertThat(testDespliegueInfraestructuraTroncalDistribucion.getMetrajeInicial()).isEqualTo(UPDATED_METRAJE_INICIAL);
        assertThat(testDespliegueInfraestructuraTroncalDistribucion.getMetrajeFinal()).isEqualTo(UPDATED_METRAJE_FINAL);
        assertThat(testDespliegueInfraestructuraTroncalDistribucion.getCalculoValorPago()).isEqualTo(UPDATED_CALCULO_VALOR_PAGO);
        assertThat(testDespliegueInfraestructuraTroncalDistribucion.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testDespliegueInfraestructuraTroncalDistribucion.getValorMetro()).isEqualTo(UPDATED_VALOR_METRO);
    }

    @Test
    @Transactional
    void patchNonExistingDespliegueInfraestructuraTroncalDistribucion() throws Exception {
        int databaseSizeBeforeUpdate = despliegueInfraestructuraTroncalDistribucionRepository.findAll().size();
        despliegueInfraestructuraTroncalDistribucion.setId(count.incrementAndGet());

        // Create the DespliegueInfraestructuraTroncalDistribucion
        DespliegueInfraestructuraTroncalDistribucionDTO despliegueInfraestructuraTroncalDistribucionDTO = despliegueInfraestructuraTroncalDistribucionMapper.toDto(
            despliegueInfraestructuraTroncalDistribucion
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDespliegueInfraestructuraTroncalDistribucionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, despliegueInfraestructuraTroncalDistribucionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(despliegueInfraestructuraTroncalDistribucionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DespliegueInfraestructuraTroncalDistribucion in the database
        List<DespliegueInfraestructuraTroncalDistribucion> despliegueInfraestructuraTroncalDistribucionList = despliegueInfraestructuraTroncalDistribucionRepository.findAll();
        assertThat(despliegueInfraestructuraTroncalDistribucionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDespliegueInfraestructuraTroncalDistribucion() throws Exception {
        int databaseSizeBeforeUpdate = despliegueInfraestructuraTroncalDistribucionRepository.findAll().size();
        despliegueInfraestructuraTroncalDistribucion.setId(count.incrementAndGet());

        // Create the DespliegueInfraestructuraTroncalDistribucion
        DespliegueInfraestructuraTroncalDistribucionDTO despliegueInfraestructuraTroncalDistribucionDTO = despliegueInfraestructuraTroncalDistribucionMapper.toDto(
            despliegueInfraestructuraTroncalDistribucion
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDespliegueInfraestructuraTroncalDistribucionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(despliegueInfraestructuraTroncalDistribucionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DespliegueInfraestructuraTroncalDistribucion in the database
        List<DespliegueInfraestructuraTroncalDistribucion> despliegueInfraestructuraTroncalDistribucionList = despliegueInfraestructuraTroncalDistribucionRepository.findAll();
        assertThat(despliegueInfraestructuraTroncalDistribucionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDespliegueInfraestructuraTroncalDistribucion() throws Exception {
        int databaseSizeBeforeUpdate = despliegueInfraestructuraTroncalDistribucionRepository.findAll().size();
        despliegueInfraestructuraTroncalDistribucion.setId(count.incrementAndGet());

        // Create the DespliegueInfraestructuraTroncalDistribucion
        DespliegueInfraestructuraTroncalDistribucionDTO despliegueInfraestructuraTroncalDistribucionDTO = despliegueInfraestructuraTroncalDistribucionMapper.toDto(
            despliegueInfraestructuraTroncalDistribucion
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDespliegueInfraestructuraTroncalDistribucionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(despliegueInfraestructuraTroncalDistribucionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DespliegueInfraestructuraTroncalDistribucion in the database
        List<DespliegueInfraestructuraTroncalDistribucion> despliegueInfraestructuraTroncalDistribucionList = despliegueInfraestructuraTroncalDistribucionRepository.findAll();
        assertThat(despliegueInfraestructuraTroncalDistribucionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDespliegueInfraestructuraTroncalDistribucion() throws Exception {
        // Initialize the database
        despliegueInfraestructuraTroncalDistribucionRepository.saveAndFlush(despliegueInfraestructuraTroncalDistribucion);

        int databaseSizeBeforeDelete = despliegueInfraestructuraTroncalDistribucionRepository.findAll().size();

        // Delete the despliegueInfraestructuraTroncalDistribucion
        restDespliegueInfraestructuraTroncalDistribucionMockMvc
            .perform(delete(ENTITY_API_URL_ID, despliegueInfraestructuraTroncalDistribucion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DespliegueInfraestructuraTroncalDistribucion> despliegueInfraestructuraTroncalDistribucionList = despliegueInfraestructuraTroncalDistribucionRepository.findAll();
        assertThat(despliegueInfraestructuraTroncalDistribucionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
