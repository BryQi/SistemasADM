package com.municipio.sistemasadm.web.rest;

import static com.municipio.sistemasadm.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.municipio.sistemasadm.IntegrationTest;
import com.municipio.sistemasadm.domain.Despliegueinfraestructuradispersion;
import com.municipio.sistemasadm.domain.enumeration.Origen;
import com.municipio.sistemasadm.repository.DespliegueinfraestructuradispersionRepository;
import com.municipio.sistemasadm.service.DespliegueinfraestructuradispersionService;
import com.municipio.sistemasadm.service.dto.DespliegueinfraestructuradispersionDTO;
import com.municipio.sistemasadm.service.mapper.DespliegueinfraestructuradispersionMapper;
import java.time.Instant;
import java.time.LocalDate;
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
 * Integration tests for the {@link DespliegueinfraestructuradispersionResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DespliegueinfraestructuradispersionResourceIT {

    private static final String DEFAULT_NOMBRE_CLIENTE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_CLIENTE = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_INSTALACION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_INSTALACION = LocalDate.now(ZoneId.systemDefault());

    private static final Origen DEFAULT_ORIGEN = Origen.MANGA;
    private static final Origen UPDATED_ORIGEN = Origen.NAP;

    private static final String DEFAULT_DESTINO = "AAAAAAAAAA";
    private static final String UPDATED_DESTINO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION_DE_POZOS_USADOS_RUTA = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION_DE_POZOS_USADOS_RUTA = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/despliegueinfraestructuradispersions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DespliegueinfraestructuradispersionRepository despliegueinfraestructuradispersionRepository;

    @Mock
    private DespliegueinfraestructuradispersionRepository despliegueinfraestructuradispersionRepositoryMock;

    @Autowired
    private DespliegueinfraestructuradispersionMapper despliegueinfraestructuradispersionMapper;

    @Mock
    private DespliegueinfraestructuradispersionService despliegueinfraestructuradispersionServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDespliegueinfraestructuradispersionMockMvc;

    private Despliegueinfraestructuradispersion despliegueinfraestructuradispersion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Despliegueinfraestructuradispersion createEntity(EntityManager em) {
        Despliegueinfraestructuradispersion despliegueinfraestructuradispersion = new Despliegueinfraestructuradispersion()
            .nombreCliente(DEFAULT_NOMBRE_CLIENTE)
            .direccion(DEFAULT_DIRECCION)
            .fechaInstalacion(DEFAULT_FECHA_INSTALACION)
            .origen(DEFAULT_ORIGEN)
            .destino(DEFAULT_DESTINO)
            .descripcionDePozosUsadosRuta(DEFAULT_DESCRIPCION_DE_POZOS_USADOS_RUTA)
            .metrajeInicial(DEFAULT_METRAJE_INICIAL)
            .metrajeFinal(DEFAULT_METRAJE_FINAL)
            .calculoValorPago(DEFAULT_CALCULO_VALOR_PAGO)
            .createdAt(DEFAULT_CREATED_AT)
            .valorMetro(DEFAULT_VALOR_METRO);
        return despliegueinfraestructuradispersion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Despliegueinfraestructuradispersion createUpdatedEntity(EntityManager em) {
        Despliegueinfraestructuradispersion despliegueinfraestructuradispersion = new Despliegueinfraestructuradispersion()
            .nombreCliente(UPDATED_NOMBRE_CLIENTE)
            .direccion(UPDATED_DIRECCION)
            .fechaInstalacion(UPDATED_FECHA_INSTALACION)
            .origen(UPDATED_ORIGEN)
            .destino(UPDATED_DESTINO)
            .descripcionDePozosUsadosRuta(UPDATED_DESCRIPCION_DE_POZOS_USADOS_RUTA)
            .metrajeInicial(UPDATED_METRAJE_INICIAL)
            .metrajeFinal(UPDATED_METRAJE_FINAL)
            .calculoValorPago(UPDATED_CALCULO_VALOR_PAGO)
            .createdAt(UPDATED_CREATED_AT)
            .valorMetro(UPDATED_VALOR_METRO);
        return despliegueinfraestructuradispersion;
    }

    @BeforeEach
    public void initTest() {
        despliegueinfraestructuradispersion = createEntity(em);
    }

    @Test
    @Transactional
    void createDespliegueinfraestructuradispersion() throws Exception {
        int databaseSizeBeforeCreate = despliegueinfraestructuradispersionRepository.findAll().size();
        // Create the Despliegueinfraestructuradispersion
        DespliegueinfraestructuradispersionDTO despliegueinfraestructuradispersionDTO = despliegueinfraestructuradispersionMapper.toDto(
            despliegueinfraestructuradispersion
        );
        restDespliegueinfraestructuradispersionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(despliegueinfraestructuradispersionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Despliegueinfraestructuradispersion in the database
        List<Despliegueinfraestructuradispersion> despliegueinfraestructuradispersionList = despliegueinfraestructuradispersionRepository.findAll();
        assertThat(despliegueinfraestructuradispersionList).hasSize(databaseSizeBeforeCreate + 1);
        Despliegueinfraestructuradispersion testDespliegueinfraestructuradispersion = despliegueinfraestructuradispersionList.get(
            despliegueinfraestructuradispersionList.size() - 1
        );
        assertThat(testDespliegueinfraestructuradispersion.getNombreCliente()).isEqualTo(DEFAULT_NOMBRE_CLIENTE);
        assertThat(testDespliegueinfraestructuradispersion.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testDespliegueinfraestructuradispersion.getFechaInstalacion()).isEqualTo(DEFAULT_FECHA_INSTALACION);
        assertThat(testDespliegueinfraestructuradispersion.getOrigen()).isEqualTo(DEFAULT_ORIGEN);
        assertThat(testDespliegueinfraestructuradispersion.getDestino()).isEqualTo(DEFAULT_DESTINO);
        assertThat(testDespliegueinfraestructuradispersion.getDescripcionDePozosUsadosRuta())
            .isEqualTo(DEFAULT_DESCRIPCION_DE_POZOS_USADOS_RUTA);
        assertThat(testDespliegueinfraestructuradispersion.getMetrajeInicial()).isEqualTo(DEFAULT_METRAJE_INICIAL);
        assertThat(testDespliegueinfraestructuradispersion.getMetrajeFinal()).isEqualTo(DEFAULT_METRAJE_FINAL);
        assertThat(testDespliegueinfraestructuradispersion.getCalculoValorPago()).isEqualTo(DEFAULT_CALCULO_VALOR_PAGO);
        assertThat(testDespliegueinfraestructuradispersion.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testDespliegueinfraestructuradispersion.getValorMetro()).isEqualTo(DEFAULT_VALOR_METRO);
    }

    @Test
    @Transactional
    void createDespliegueinfraestructuradispersionWithExistingId() throws Exception {
        // Create the Despliegueinfraestructuradispersion with an existing ID
        despliegueinfraestructuradispersion.setId(1L);
        DespliegueinfraestructuradispersionDTO despliegueinfraestructuradispersionDTO = despliegueinfraestructuradispersionMapper.toDto(
            despliegueinfraestructuradispersion
        );

        int databaseSizeBeforeCreate = despliegueinfraestructuradispersionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDespliegueinfraestructuradispersionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(despliegueinfraestructuradispersionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Despliegueinfraestructuradispersion in the database
        List<Despliegueinfraestructuradispersion> despliegueinfraestructuradispersionList = despliegueinfraestructuradispersionRepository.findAll();
        assertThat(despliegueinfraestructuradispersionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreClienteIsRequired() throws Exception {
        int databaseSizeBeforeTest = despliegueinfraestructuradispersionRepository.findAll().size();
        // set the field null
        despliegueinfraestructuradispersion.setNombreCliente(null);

        // Create the Despliegueinfraestructuradispersion, which fails.
        DespliegueinfraestructuradispersionDTO despliegueinfraestructuradispersionDTO = despliegueinfraestructuradispersionMapper.toDto(
            despliegueinfraestructuradispersion
        );

        restDespliegueinfraestructuradispersionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(despliegueinfraestructuradispersionDTO))
            )
            .andExpect(status().isBadRequest());

        List<Despliegueinfraestructuradispersion> despliegueinfraestructuradispersionList = despliegueinfraestructuradispersionRepository.findAll();
        assertThat(despliegueinfraestructuradispersionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDireccionIsRequired() throws Exception {
        int databaseSizeBeforeTest = despliegueinfraestructuradispersionRepository.findAll().size();
        // set the field null
        despliegueinfraestructuradispersion.setDireccion(null);

        // Create the Despliegueinfraestructuradispersion, which fails.
        DespliegueinfraestructuradispersionDTO despliegueinfraestructuradispersionDTO = despliegueinfraestructuradispersionMapper.toDto(
            despliegueinfraestructuradispersion
        );

        restDespliegueinfraestructuradispersionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(despliegueinfraestructuradispersionDTO))
            )
            .andExpect(status().isBadRequest());

        List<Despliegueinfraestructuradispersion> despliegueinfraestructuradispersionList = despliegueinfraestructuradispersionRepository.findAll();
        assertThat(despliegueinfraestructuradispersionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaInstalacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = despliegueinfraestructuradispersionRepository.findAll().size();
        // set the field null
        despliegueinfraestructuradispersion.setFechaInstalacion(null);

        // Create the Despliegueinfraestructuradispersion, which fails.
        DespliegueinfraestructuradispersionDTO despliegueinfraestructuradispersionDTO = despliegueinfraestructuradispersionMapper.toDto(
            despliegueinfraestructuradispersion
        );

        restDespliegueinfraestructuradispersionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(despliegueinfraestructuradispersionDTO))
            )
            .andExpect(status().isBadRequest());

        List<Despliegueinfraestructuradispersion> despliegueinfraestructuradispersionList = despliegueinfraestructuradispersionRepository.findAll();
        assertThat(despliegueinfraestructuradispersionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDestinoIsRequired() throws Exception {
        int databaseSizeBeforeTest = despliegueinfraestructuradispersionRepository.findAll().size();
        // set the field null
        despliegueinfraestructuradispersion.setDestino(null);

        // Create the Despliegueinfraestructuradispersion, which fails.
        DespliegueinfraestructuradispersionDTO despliegueinfraestructuradispersionDTO = despliegueinfraestructuradispersionMapper.toDto(
            despliegueinfraestructuradispersion
        );

        restDespliegueinfraestructuradispersionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(despliegueinfraestructuradispersionDTO))
            )
            .andExpect(status().isBadRequest());

        List<Despliegueinfraestructuradispersion> despliegueinfraestructuradispersionList = despliegueinfraestructuradispersionRepository.findAll();
        assertThat(despliegueinfraestructuradispersionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescripcionDePozosUsadosRutaIsRequired() throws Exception {
        int databaseSizeBeforeTest = despliegueinfraestructuradispersionRepository.findAll().size();
        // set the field null
        despliegueinfraestructuradispersion.setDescripcionDePozosUsadosRuta(null);

        // Create the Despliegueinfraestructuradispersion, which fails.
        DespliegueinfraestructuradispersionDTO despliegueinfraestructuradispersionDTO = despliegueinfraestructuradispersionMapper.toDto(
            despliegueinfraestructuradispersion
        );

        restDespliegueinfraestructuradispersionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(despliegueinfraestructuradispersionDTO))
            )
            .andExpect(status().isBadRequest());

        List<Despliegueinfraestructuradispersion> despliegueinfraestructuradispersionList = despliegueinfraestructuradispersionRepository.findAll();
        assertThat(despliegueinfraestructuradispersionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMetrajeInicialIsRequired() throws Exception {
        int databaseSizeBeforeTest = despliegueinfraestructuradispersionRepository.findAll().size();
        // set the field null
        despliegueinfraestructuradispersion.setMetrajeInicial(null);

        // Create the Despliegueinfraestructuradispersion, which fails.
        DespliegueinfraestructuradispersionDTO despliegueinfraestructuradispersionDTO = despliegueinfraestructuradispersionMapper.toDto(
            despliegueinfraestructuradispersion
        );

        restDespliegueinfraestructuradispersionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(despliegueinfraestructuradispersionDTO))
            )
            .andExpect(status().isBadRequest());

        List<Despliegueinfraestructuradispersion> despliegueinfraestructuradispersionList = despliegueinfraestructuradispersionRepository.findAll();
        assertThat(despliegueinfraestructuradispersionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMetrajeFinalIsRequired() throws Exception {
        int databaseSizeBeforeTest = despliegueinfraestructuradispersionRepository.findAll().size();
        // set the field null
        despliegueinfraestructuradispersion.setMetrajeFinal(null);

        // Create the Despliegueinfraestructuradispersion, which fails.
        DespliegueinfraestructuradispersionDTO despliegueinfraestructuradispersionDTO = despliegueinfraestructuradispersionMapper.toDto(
            despliegueinfraestructuradispersion
        );

        restDespliegueinfraestructuradispersionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(despliegueinfraestructuradispersionDTO))
            )
            .andExpect(status().isBadRequest());

        List<Despliegueinfraestructuradispersion> despliegueinfraestructuradispersionList = despliegueinfraestructuradispersionRepository.findAll();
        assertThat(despliegueinfraestructuradispersionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCalculoValorPagoIsRequired() throws Exception {
        int databaseSizeBeforeTest = despliegueinfraestructuradispersionRepository.findAll().size();
        // set the field null
        despliegueinfraestructuradispersion.setCalculoValorPago(null);

        // Create the Despliegueinfraestructuradispersion, which fails.
        DespliegueinfraestructuradispersionDTO despliegueinfraestructuradispersionDTO = despliegueinfraestructuradispersionMapper.toDto(
            despliegueinfraestructuradispersion
        );

        restDespliegueinfraestructuradispersionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(despliegueinfraestructuradispersionDTO))
            )
            .andExpect(status().isBadRequest());

        List<Despliegueinfraestructuradispersion> despliegueinfraestructuradispersionList = despliegueinfraestructuradispersionRepository.findAll();
        assertThat(despliegueinfraestructuradispersionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = despliegueinfraestructuradispersionRepository.findAll().size();
        // set the field null
        despliegueinfraestructuradispersion.setCreatedAt(null);

        // Create the Despliegueinfraestructuradispersion, which fails.
        DespliegueinfraestructuradispersionDTO despliegueinfraestructuradispersionDTO = despliegueinfraestructuradispersionMapper.toDto(
            despliegueinfraestructuradispersion
        );

        restDespliegueinfraestructuradispersionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(despliegueinfraestructuradispersionDTO))
            )
            .andExpect(status().isBadRequest());

        List<Despliegueinfraestructuradispersion> despliegueinfraestructuradispersionList = despliegueinfraestructuradispersionRepository.findAll();
        assertThat(despliegueinfraestructuradispersionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValorMetroIsRequired() throws Exception {
        int databaseSizeBeforeTest = despliegueinfraestructuradispersionRepository.findAll().size();
        // set the field null
        despliegueinfraestructuradispersion.setValorMetro(null);

        // Create the Despliegueinfraestructuradispersion, which fails.
        DespliegueinfraestructuradispersionDTO despliegueinfraestructuradispersionDTO = despliegueinfraestructuradispersionMapper.toDto(
            despliegueinfraestructuradispersion
        );

        restDespliegueinfraestructuradispersionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(despliegueinfraestructuradispersionDTO))
            )
            .andExpect(status().isBadRequest());

        List<Despliegueinfraestructuradispersion> despliegueinfraestructuradispersionList = despliegueinfraestructuradispersionRepository.findAll();
        assertThat(despliegueinfraestructuradispersionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDespliegueinfraestructuradispersions() throws Exception {
        // Initialize the database
        despliegueinfraestructuradispersionRepository.saveAndFlush(despliegueinfraestructuradispersion);

        // Get all the despliegueinfraestructuradispersionList
        restDespliegueinfraestructuradispersionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(despliegueinfraestructuradispersion.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreCliente").value(hasItem(DEFAULT_NOMBRE_CLIENTE)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].fechaInstalacion").value(hasItem(DEFAULT_FECHA_INSTALACION.toString())))
            .andExpect(jsonPath("$.[*].origen").value(hasItem(DEFAULT_ORIGEN.toString())))
            .andExpect(jsonPath("$.[*].destino").value(hasItem(DEFAULT_DESTINO)))
            .andExpect(jsonPath("$.[*].descripcionDePozosUsadosRuta").value(hasItem(DEFAULT_DESCRIPCION_DE_POZOS_USADOS_RUTA)))
            .andExpect(jsonPath("$.[*].metrajeInicial").value(hasItem(DEFAULT_METRAJE_INICIAL.doubleValue())))
            .andExpect(jsonPath("$.[*].metrajeFinal").value(hasItem(DEFAULT_METRAJE_FINAL.doubleValue())))
            .andExpect(jsonPath("$.[*].calculoValorPago").value(hasItem(DEFAULT_CALCULO_VALOR_PAGO.doubleValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].valorMetro").value(hasItem(DEFAULT_VALOR_METRO.doubleValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDespliegueinfraestructuradispersionsWithEagerRelationshipsIsEnabled() throws Exception {
        when(despliegueinfraestructuradispersionServiceMock.findAllWithEagerRelationships(any()))
            .thenReturn(new PageImpl(new ArrayList<>()));

        restDespliegueinfraestructuradispersionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(despliegueinfraestructuradispersionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDespliegueinfraestructuradispersionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(despliegueinfraestructuradispersionServiceMock.findAllWithEagerRelationships(any()))
            .thenReturn(new PageImpl(new ArrayList<>()));

        restDespliegueinfraestructuradispersionMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(despliegueinfraestructuradispersionRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDespliegueinfraestructuradispersion() throws Exception {
        // Initialize the database
        despliegueinfraestructuradispersionRepository.saveAndFlush(despliegueinfraestructuradispersion);

        // Get the despliegueinfraestructuradispersion
        restDespliegueinfraestructuradispersionMockMvc
            .perform(get(ENTITY_API_URL_ID, despliegueinfraestructuradispersion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(despliegueinfraestructuradispersion.getId().intValue()))
            .andExpect(jsonPath("$.nombreCliente").value(DEFAULT_NOMBRE_CLIENTE))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION))
            .andExpect(jsonPath("$.fechaInstalacion").value(DEFAULT_FECHA_INSTALACION.toString()))
            .andExpect(jsonPath("$.origen").value(DEFAULT_ORIGEN.toString()))
            .andExpect(jsonPath("$.destino").value(DEFAULT_DESTINO))
            .andExpect(jsonPath("$.descripcionDePozosUsadosRuta").value(DEFAULT_DESCRIPCION_DE_POZOS_USADOS_RUTA))
            .andExpect(jsonPath("$.metrajeInicial").value(DEFAULT_METRAJE_INICIAL.doubleValue()))
            .andExpect(jsonPath("$.metrajeFinal").value(DEFAULT_METRAJE_FINAL.doubleValue()))
            .andExpect(jsonPath("$.calculoValorPago").value(DEFAULT_CALCULO_VALOR_PAGO.doubleValue()))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.valorMetro").value(DEFAULT_VALOR_METRO.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingDespliegueinfraestructuradispersion() throws Exception {
        // Get the despliegueinfraestructuradispersion
        restDespliegueinfraestructuradispersionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDespliegueinfraestructuradispersion() throws Exception {
        // Initialize the database
        despliegueinfraestructuradispersionRepository.saveAndFlush(despliegueinfraestructuradispersion);

        int databaseSizeBeforeUpdate = despliegueinfraestructuradispersionRepository.findAll().size();

        // Update the despliegueinfraestructuradispersion
        Despliegueinfraestructuradispersion updatedDespliegueinfraestructuradispersion = despliegueinfraestructuradispersionRepository
            .findById(despliegueinfraestructuradispersion.getId())
            .get();
        // Disconnect from session so that the updates on updatedDespliegueinfraestructuradispersion are not directly saved in db
        em.detach(updatedDespliegueinfraestructuradispersion);
        updatedDespliegueinfraestructuradispersion
            .nombreCliente(UPDATED_NOMBRE_CLIENTE)
            .direccion(UPDATED_DIRECCION)
            .fechaInstalacion(UPDATED_FECHA_INSTALACION)
            .origen(UPDATED_ORIGEN)
            .destino(UPDATED_DESTINO)
            .descripcionDePozosUsadosRuta(UPDATED_DESCRIPCION_DE_POZOS_USADOS_RUTA)
            .metrajeInicial(UPDATED_METRAJE_INICIAL)
            .metrajeFinal(UPDATED_METRAJE_FINAL)
            .calculoValorPago(UPDATED_CALCULO_VALOR_PAGO)
            .createdAt(UPDATED_CREATED_AT)
            .valorMetro(UPDATED_VALOR_METRO);
        DespliegueinfraestructuradispersionDTO despliegueinfraestructuradispersionDTO = despliegueinfraestructuradispersionMapper.toDto(
            updatedDespliegueinfraestructuradispersion
        );

        restDespliegueinfraestructuradispersionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, despliegueinfraestructuradispersionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(despliegueinfraestructuradispersionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Despliegueinfraestructuradispersion in the database
        List<Despliegueinfraestructuradispersion> despliegueinfraestructuradispersionList = despliegueinfraestructuradispersionRepository.findAll();
        assertThat(despliegueinfraestructuradispersionList).hasSize(databaseSizeBeforeUpdate);
        Despliegueinfraestructuradispersion testDespliegueinfraestructuradispersion = despliegueinfraestructuradispersionList.get(
            despliegueinfraestructuradispersionList.size() - 1
        );
        assertThat(testDespliegueinfraestructuradispersion.getNombreCliente()).isEqualTo(UPDATED_NOMBRE_CLIENTE);
        assertThat(testDespliegueinfraestructuradispersion.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testDespliegueinfraestructuradispersion.getFechaInstalacion()).isEqualTo(UPDATED_FECHA_INSTALACION);
        assertThat(testDespliegueinfraestructuradispersion.getOrigen()).isEqualTo(UPDATED_ORIGEN);
        assertThat(testDespliegueinfraestructuradispersion.getDestino()).isEqualTo(UPDATED_DESTINO);
        assertThat(testDespliegueinfraestructuradispersion.getDescripcionDePozosUsadosRuta())
            .isEqualTo(UPDATED_DESCRIPCION_DE_POZOS_USADOS_RUTA);
        assertThat(testDespliegueinfraestructuradispersion.getMetrajeInicial()).isEqualTo(UPDATED_METRAJE_INICIAL);
        assertThat(testDespliegueinfraestructuradispersion.getMetrajeFinal()).isEqualTo(UPDATED_METRAJE_FINAL);
        assertThat(testDespliegueinfraestructuradispersion.getCalculoValorPago()).isEqualTo(UPDATED_CALCULO_VALOR_PAGO);
        assertThat(testDespliegueinfraestructuradispersion.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testDespliegueinfraestructuradispersion.getValorMetro()).isEqualTo(UPDATED_VALOR_METRO);
    }

    @Test
    @Transactional
    void putNonExistingDespliegueinfraestructuradispersion() throws Exception {
        int databaseSizeBeforeUpdate = despliegueinfraestructuradispersionRepository.findAll().size();
        despliegueinfraestructuradispersion.setId(count.incrementAndGet());

        // Create the Despliegueinfraestructuradispersion
        DespliegueinfraestructuradispersionDTO despliegueinfraestructuradispersionDTO = despliegueinfraestructuradispersionMapper.toDto(
            despliegueinfraestructuradispersion
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDespliegueinfraestructuradispersionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, despliegueinfraestructuradispersionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(despliegueinfraestructuradispersionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Despliegueinfraestructuradispersion in the database
        List<Despliegueinfraestructuradispersion> despliegueinfraestructuradispersionList = despliegueinfraestructuradispersionRepository.findAll();
        assertThat(despliegueinfraestructuradispersionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDespliegueinfraestructuradispersion() throws Exception {
        int databaseSizeBeforeUpdate = despliegueinfraestructuradispersionRepository.findAll().size();
        despliegueinfraestructuradispersion.setId(count.incrementAndGet());

        // Create the Despliegueinfraestructuradispersion
        DespliegueinfraestructuradispersionDTO despliegueinfraestructuradispersionDTO = despliegueinfraestructuradispersionMapper.toDto(
            despliegueinfraestructuradispersion
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDespliegueinfraestructuradispersionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(despliegueinfraestructuradispersionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Despliegueinfraestructuradispersion in the database
        List<Despliegueinfraestructuradispersion> despliegueinfraestructuradispersionList = despliegueinfraestructuradispersionRepository.findAll();
        assertThat(despliegueinfraestructuradispersionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDespliegueinfraestructuradispersion() throws Exception {
        int databaseSizeBeforeUpdate = despliegueinfraestructuradispersionRepository.findAll().size();
        despliegueinfraestructuradispersion.setId(count.incrementAndGet());

        // Create the Despliegueinfraestructuradispersion
        DespliegueinfraestructuradispersionDTO despliegueinfraestructuradispersionDTO = despliegueinfraestructuradispersionMapper.toDto(
            despliegueinfraestructuradispersion
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDespliegueinfraestructuradispersionMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(despliegueinfraestructuradispersionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Despliegueinfraestructuradispersion in the database
        List<Despliegueinfraestructuradispersion> despliegueinfraestructuradispersionList = despliegueinfraestructuradispersionRepository.findAll();
        assertThat(despliegueinfraestructuradispersionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDespliegueinfraestructuradispersionWithPatch() throws Exception {
        // Initialize the database
        despliegueinfraestructuradispersionRepository.saveAndFlush(despliegueinfraestructuradispersion);

        int databaseSizeBeforeUpdate = despliegueinfraestructuradispersionRepository.findAll().size();

        // Update the despliegueinfraestructuradispersion using partial update
        Despliegueinfraestructuradispersion partialUpdatedDespliegueinfraestructuradispersion = new Despliegueinfraestructuradispersion();
        partialUpdatedDespliegueinfraestructuradispersion.setId(despliegueinfraestructuradispersion.getId());

        partialUpdatedDespliegueinfraestructuradispersion
            .nombreCliente(UPDATED_NOMBRE_CLIENTE)
            .origen(UPDATED_ORIGEN)
            .destino(UPDATED_DESTINO)
            .descripcionDePozosUsadosRuta(UPDATED_DESCRIPCION_DE_POZOS_USADOS_RUTA)
            .metrajeFinal(UPDATED_METRAJE_FINAL)
            .calculoValorPago(UPDATED_CALCULO_VALOR_PAGO)
            .createdAt(UPDATED_CREATED_AT)
            .valorMetro(UPDATED_VALOR_METRO);

        restDespliegueinfraestructuradispersionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDespliegueinfraestructuradispersion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDespliegueinfraestructuradispersion))
            )
            .andExpect(status().isOk());

        // Validate the Despliegueinfraestructuradispersion in the database
        List<Despliegueinfraestructuradispersion> despliegueinfraestructuradispersionList = despliegueinfraestructuradispersionRepository.findAll();
        assertThat(despliegueinfraestructuradispersionList).hasSize(databaseSizeBeforeUpdate);
        Despliegueinfraestructuradispersion testDespliegueinfraestructuradispersion = despliegueinfraestructuradispersionList.get(
            despliegueinfraestructuradispersionList.size() - 1
        );
        assertThat(testDespliegueinfraestructuradispersion.getNombreCliente()).isEqualTo(UPDATED_NOMBRE_CLIENTE);
        assertThat(testDespliegueinfraestructuradispersion.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testDespliegueinfraestructuradispersion.getFechaInstalacion()).isEqualTo(DEFAULT_FECHA_INSTALACION);
        assertThat(testDespliegueinfraestructuradispersion.getOrigen()).isEqualTo(UPDATED_ORIGEN);
        assertThat(testDespliegueinfraestructuradispersion.getDestino()).isEqualTo(UPDATED_DESTINO);
        assertThat(testDespliegueinfraestructuradispersion.getDescripcionDePozosUsadosRuta())
            .isEqualTo(UPDATED_DESCRIPCION_DE_POZOS_USADOS_RUTA);
        assertThat(testDespliegueinfraestructuradispersion.getMetrajeInicial()).isEqualTo(DEFAULT_METRAJE_INICIAL);
        assertThat(testDespliegueinfraestructuradispersion.getMetrajeFinal()).isEqualTo(UPDATED_METRAJE_FINAL);
        assertThat(testDespliegueinfraestructuradispersion.getCalculoValorPago()).isEqualTo(UPDATED_CALCULO_VALOR_PAGO);
        assertThat(testDespliegueinfraestructuradispersion.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testDespliegueinfraestructuradispersion.getValorMetro()).isEqualTo(UPDATED_VALOR_METRO);
    }

    @Test
    @Transactional
    void fullUpdateDespliegueinfraestructuradispersionWithPatch() throws Exception {
        // Initialize the database
        despliegueinfraestructuradispersionRepository.saveAndFlush(despliegueinfraestructuradispersion);

        int databaseSizeBeforeUpdate = despliegueinfraestructuradispersionRepository.findAll().size();

        // Update the despliegueinfraestructuradispersion using partial update
        Despliegueinfraestructuradispersion partialUpdatedDespliegueinfraestructuradispersion = new Despliegueinfraestructuradispersion();
        partialUpdatedDespliegueinfraestructuradispersion.setId(despliegueinfraestructuradispersion.getId());

        partialUpdatedDespliegueinfraestructuradispersion
            .nombreCliente(UPDATED_NOMBRE_CLIENTE)
            .direccion(UPDATED_DIRECCION)
            .fechaInstalacion(UPDATED_FECHA_INSTALACION)
            .origen(UPDATED_ORIGEN)
            .destino(UPDATED_DESTINO)
            .descripcionDePozosUsadosRuta(UPDATED_DESCRIPCION_DE_POZOS_USADOS_RUTA)
            .metrajeInicial(UPDATED_METRAJE_INICIAL)
            .metrajeFinal(UPDATED_METRAJE_FINAL)
            .calculoValorPago(UPDATED_CALCULO_VALOR_PAGO)
            .createdAt(UPDATED_CREATED_AT)
            .valorMetro(UPDATED_VALOR_METRO);

        restDespliegueinfraestructuradispersionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDespliegueinfraestructuradispersion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDespliegueinfraestructuradispersion))
            )
            .andExpect(status().isOk());

        // Validate the Despliegueinfraestructuradispersion in the database
        List<Despliegueinfraestructuradispersion> despliegueinfraestructuradispersionList = despliegueinfraestructuradispersionRepository.findAll();
        assertThat(despliegueinfraestructuradispersionList).hasSize(databaseSizeBeforeUpdate);
        Despliegueinfraestructuradispersion testDespliegueinfraestructuradispersion = despliegueinfraestructuradispersionList.get(
            despliegueinfraestructuradispersionList.size() - 1
        );
        assertThat(testDespliegueinfraestructuradispersion.getNombreCliente()).isEqualTo(UPDATED_NOMBRE_CLIENTE);
        assertThat(testDespliegueinfraestructuradispersion.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testDespliegueinfraestructuradispersion.getFechaInstalacion()).isEqualTo(UPDATED_FECHA_INSTALACION);
        assertThat(testDespliegueinfraestructuradispersion.getOrigen()).isEqualTo(UPDATED_ORIGEN);
        assertThat(testDespliegueinfraestructuradispersion.getDestino()).isEqualTo(UPDATED_DESTINO);
        assertThat(testDespliegueinfraestructuradispersion.getDescripcionDePozosUsadosRuta())
            .isEqualTo(UPDATED_DESCRIPCION_DE_POZOS_USADOS_RUTA);
        assertThat(testDespliegueinfraestructuradispersion.getMetrajeInicial()).isEqualTo(UPDATED_METRAJE_INICIAL);
        assertThat(testDespliegueinfraestructuradispersion.getMetrajeFinal()).isEqualTo(UPDATED_METRAJE_FINAL);
        assertThat(testDespliegueinfraestructuradispersion.getCalculoValorPago()).isEqualTo(UPDATED_CALCULO_VALOR_PAGO);
        assertThat(testDespliegueinfraestructuradispersion.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testDespliegueinfraestructuradispersion.getValorMetro()).isEqualTo(UPDATED_VALOR_METRO);
    }

    @Test
    @Transactional
    void patchNonExistingDespliegueinfraestructuradispersion() throws Exception {
        int databaseSizeBeforeUpdate = despliegueinfraestructuradispersionRepository.findAll().size();
        despliegueinfraestructuradispersion.setId(count.incrementAndGet());

        // Create the Despliegueinfraestructuradispersion
        DespliegueinfraestructuradispersionDTO despliegueinfraestructuradispersionDTO = despliegueinfraestructuradispersionMapper.toDto(
            despliegueinfraestructuradispersion
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDespliegueinfraestructuradispersionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, despliegueinfraestructuradispersionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(despliegueinfraestructuradispersionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Despliegueinfraestructuradispersion in the database
        List<Despliegueinfraestructuradispersion> despliegueinfraestructuradispersionList = despliegueinfraestructuradispersionRepository.findAll();
        assertThat(despliegueinfraestructuradispersionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDespliegueinfraestructuradispersion() throws Exception {
        int databaseSizeBeforeUpdate = despliegueinfraestructuradispersionRepository.findAll().size();
        despliegueinfraestructuradispersion.setId(count.incrementAndGet());

        // Create the Despliegueinfraestructuradispersion
        DespliegueinfraestructuradispersionDTO despliegueinfraestructuradispersionDTO = despliegueinfraestructuradispersionMapper.toDto(
            despliegueinfraestructuradispersion
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDespliegueinfraestructuradispersionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(despliegueinfraestructuradispersionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Despliegueinfraestructuradispersion in the database
        List<Despliegueinfraestructuradispersion> despliegueinfraestructuradispersionList = despliegueinfraestructuradispersionRepository.findAll();
        assertThat(despliegueinfraestructuradispersionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDespliegueinfraestructuradispersion() throws Exception {
        int databaseSizeBeforeUpdate = despliegueinfraestructuradispersionRepository.findAll().size();
        despliegueinfraestructuradispersion.setId(count.incrementAndGet());

        // Create the Despliegueinfraestructuradispersion
        DespliegueinfraestructuradispersionDTO despliegueinfraestructuradispersionDTO = despliegueinfraestructuradispersionMapper.toDto(
            despliegueinfraestructuradispersion
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDespliegueinfraestructuradispersionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(despliegueinfraestructuradispersionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Despliegueinfraestructuradispersion in the database
        List<Despliegueinfraestructuradispersion> despliegueinfraestructuradispersionList = despliegueinfraestructuradispersionRepository.findAll();
        assertThat(despliegueinfraestructuradispersionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDespliegueinfraestructuradispersion() throws Exception {
        // Initialize the database
        despliegueinfraestructuradispersionRepository.saveAndFlush(despliegueinfraestructuradispersion);

        int databaseSizeBeforeDelete = despliegueinfraestructuradispersionRepository.findAll().size();

        // Delete the despliegueinfraestructuradispersion
        restDespliegueinfraestructuradispersionMockMvc
            .perform(delete(ENTITY_API_URL_ID, despliegueinfraestructuradispersion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Despliegueinfraestructuradispersion> despliegueinfraestructuradispersionList = despliegueinfraestructuradispersionRepository.findAll();
        assertThat(despliegueinfraestructuradispersionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
