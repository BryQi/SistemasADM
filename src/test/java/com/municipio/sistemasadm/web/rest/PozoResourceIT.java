package com.municipio.sistemasadm.web.rest;

import static com.municipio.sistemasadm.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.municipio.sistemasadm.IntegrationTest;
import com.municipio.sistemasadm.domain.Pozo;
import com.municipio.sistemasadm.domain.enumeration.TipoPozo;
import com.municipio.sistemasadm.repository.PozoRepository;
import com.municipio.sistemasadm.service.dto.PozoDTO;
import com.municipio.sistemasadm.service.mapper.PozoMapper;
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
 * Integration tests for the {@link PozoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PozoResourceIT {

    private static final String DEFAULT_NUMEROPOZO = "AAAAAAAAAA";
    private static final String UPDATED_NUMEROPOZO = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final TipoPozo DEFAULT_TIPOPOZO = TipoPozo.PozodePaso;
    private static final TipoPozo UPDATED_TIPOPOZO = TipoPozo.Revision;

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_LONGITUD = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUD = "BBBBBBBBBB";

    private static final String DEFAULT_LATITUD = "AAAAAAAAAA";
    private static final String UPDATED_LATITUD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/pozos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PozoRepository pozoRepository;

    @Autowired
    private PozoMapper pozoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPozoMockMvc;

    private Pozo pozo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pozo createEntity(EntityManager em) {
        Pozo pozo = new Pozo()
            .numeropozo(DEFAULT_NUMEROPOZO)
            .direccion(DEFAULT_DIRECCION)
            .tipopozo(DEFAULT_TIPOPOZO)
            .createdAt(DEFAULT_CREATED_AT)
            .longitud(DEFAULT_LONGITUD)
            .latitud(DEFAULT_LATITUD);
        return pozo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pozo createUpdatedEntity(EntityManager em) {
        Pozo pozo = new Pozo()
            .numeropozo(UPDATED_NUMEROPOZO)
            .direccion(UPDATED_DIRECCION)
            .tipopozo(UPDATED_TIPOPOZO)
            .createdAt(UPDATED_CREATED_AT)
            .longitud(UPDATED_LONGITUD)
            .latitud(UPDATED_LATITUD);
        return pozo;
    }

    @BeforeEach
    public void initTest() {
        pozo = createEntity(em);
    }

    @Test
    @Transactional
    void createPozo() throws Exception {
        int databaseSizeBeforeCreate = pozoRepository.findAll().size();
        // Create the Pozo
        PozoDTO pozoDTO = pozoMapper.toDto(pozo);
        restPozoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pozoDTO)))
            .andExpect(status().isCreated());

        // Validate the Pozo in the database
        List<Pozo> pozoList = pozoRepository.findAll();
        assertThat(pozoList).hasSize(databaseSizeBeforeCreate + 1);
        Pozo testPozo = pozoList.get(pozoList.size() - 1);
        assertThat(testPozo.getNumeropozo()).isEqualTo(DEFAULT_NUMEROPOZO);
        assertThat(testPozo.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testPozo.getTipopozo()).isEqualTo(DEFAULT_TIPOPOZO);
        assertThat(testPozo.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testPozo.getLongitud()).isEqualTo(DEFAULT_LONGITUD);
        assertThat(testPozo.getLatitud()).isEqualTo(DEFAULT_LATITUD);
    }

    @Test
    @Transactional
    void createPozoWithExistingId() throws Exception {
        // Create the Pozo with an existing ID
        pozo.setId(1L);
        PozoDTO pozoDTO = pozoMapper.toDto(pozo);

        int databaseSizeBeforeCreate = pozoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPozoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pozoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pozo in the database
        List<Pozo> pozoList = pozoRepository.findAll();
        assertThat(pozoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNumeropozoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pozoRepository.findAll().size();
        // set the field null
        pozo.setNumeropozo(null);

        // Create the Pozo, which fails.
        PozoDTO pozoDTO = pozoMapper.toDto(pozo);

        restPozoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pozoDTO)))
            .andExpect(status().isBadRequest());

        List<Pozo> pozoList = pozoRepository.findAll();
        assertThat(pozoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDireccionIsRequired() throws Exception {
        int databaseSizeBeforeTest = pozoRepository.findAll().size();
        // set the field null
        pozo.setDireccion(null);

        // Create the Pozo, which fails.
        PozoDTO pozoDTO = pozoMapper.toDto(pozo);

        restPozoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pozoDTO)))
            .andExpect(status().isBadRequest());

        List<Pozo> pozoList = pozoRepository.findAll();
        assertThat(pozoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = pozoRepository.findAll().size();
        // set the field null
        pozo.setCreatedAt(null);

        // Create the Pozo, which fails.
        PozoDTO pozoDTO = pozoMapper.toDto(pozo);

        restPozoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pozoDTO)))
            .andExpect(status().isBadRequest());

        List<Pozo> pozoList = pozoRepository.findAll();
        assertThat(pozoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLongitudIsRequired() throws Exception {
        int databaseSizeBeforeTest = pozoRepository.findAll().size();
        // set the field null
        pozo.setLongitud(null);

        // Create the Pozo, which fails.
        PozoDTO pozoDTO = pozoMapper.toDto(pozo);

        restPozoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pozoDTO)))
            .andExpect(status().isBadRequest());

        List<Pozo> pozoList = pozoRepository.findAll();
        assertThat(pozoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLatitudIsRequired() throws Exception {
        int databaseSizeBeforeTest = pozoRepository.findAll().size();
        // set the field null
        pozo.setLatitud(null);

        // Create the Pozo, which fails.
        PozoDTO pozoDTO = pozoMapper.toDto(pozo);

        restPozoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pozoDTO)))
            .andExpect(status().isBadRequest());

        List<Pozo> pozoList = pozoRepository.findAll();
        assertThat(pozoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPozos() throws Exception {
        // Initialize the database
        pozoRepository.saveAndFlush(pozo);

        // Get all the pozoList
        restPozoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pozo.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeropozo").value(hasItem(DEFAULT_NUMEROPOZO)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].tipopozo").value(hasItem(DEFAULT_TIPOPOZO.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].longitud").value(hasItem(DEFAULT_LONGITUD)))
            .andExpect(jsonPath("$.[*].latitud").value(hasItem(DEFAULT_LATITUD)));
    }

    @Test
    @Transactional
    void getPozo() throws Exception {
        // Initialize the database
        pozoRepository.saveAndFlush(pozo);

        // Get the pozo
        restPozoMockMvc
            .perform(get(ENTITY_API_URL_ID, pozo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pozo.getId().intValue()))
            .andExpect(jsonPath("$.numeropozo").value(DEFAULT_NUMEROPOZO))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION))
            .andExpect(jsonPath("$.tipopozo").value(DEFAULT_TIPOPOZO.toString()))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.longitud").value(DEFAULT_LONGITUD))
            .andExpect(jsonPath("$.latitud").value(DEFAULT_LATITUD));
    }

    @Test
    @Transactional
    void getNonExistingPozo() throws Exception {
        // Get the pozo
        restPozoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPozo() throws Exception {
        // Initialize the database
        pozoRepository.saveAndFlush(pozo);

        int databaseSizeBeforeUpdate = pozoRepository.findAll().size();

        // Update the pozo
        Pozo updatedPozo = pozoRepository.findById(pozo.getId()).get();
        // Disconnect from session so that the updates on updatedPozo are not directly saved in db
        em.detach(updatedPozo);
        updatedPozo
            .numeropozo(UPDATED_NUMEROPOZO)
            .direccion(UPDATED_DIRECCION)
            .tipopozo(UPDATED_TIPOPOZO)
            .createdAt(UPDATED_CREATED_AT)
            .longitud(UPDATED_LONGITUD)
            .latitud(UPDATED_LATITUD);
        PozoDTO pozoDTO = pozoMapper.toDto(updatedPozo);

        restPozoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pozoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pozoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Pozo in the database
        List<Pozo> pozoList = pozoRepository.findAll();
        assertThat(pozoList).hasSize(databaseSizeBeforeUpdate);
        Pozo testPozo = pozoList.get(pozoList.size() - 1);
        assertThat(testPozo.getNumeropozo()).isEqualTo(UPDATED_NUMEROPOZO);
        assertThat(testPozo.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testPozo.getTipopozo()).isEqualTo(UPDATED_TIPOPOZO);
        assertThat(testPozo.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPozo.getLongitud()).isEqualTo(UPDATED_LONGITUD);
        assertThat(testPozo.getLatitud()).isEqualTo(UPDATED_LATITUD);
    }

    @Test
    @Transactional
    void putNonExistingPozo() throws Exception {
        int databaseSizeBeforeUpdate = pozoRepository.findAll().size();
        pozo.setId(count.incrementAndGet());

        // Create the Pozo
        PozoDTO pozoDTO = pozoMapper.toDto(pozo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPozoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pozoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pozoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pozo in the database
        List<Pozo> pozoList = pozoRepository.findAll();
        assertThat(pozoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPozo() throws Exception {
        int databaseSizeBeforeUpdate = pozoRepository.findAll().size();
        pozo.setId(count.incrementAndGet());

        // Create the Pozo
        PozoDTO pozoDTO = pozoMapper.toDto(pozo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPozoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pozoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pozo in the database
        List<Pozo> pozoList = pozoRepository.findAll();
        assertThat(pozoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPozo() throws Exception {
        int databaseSizeBeforeUpdate = pozoRepository.findAll().size();
        pozo.setId(count.incrementAndGet());

        // Create the Pozo
        PozoDTO pozoDTO = pozoMapper.toDto(pozo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPozoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pozoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pozo in the database
        List<Pozo> pozoList = pozoRepository.findAll();
        assertThat(pozoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePozoWithPatch() throws Exception {
        // Initialize the database
        pozoRepository.saveAndFlush(pozo);

        int databaseSizeBeforeUpdate = pozoRepository.findAll().size();

        // Update the pozo using partial update
        Pozo partialUpdatedPozo = new Pozo();
        partialUpdatedPozo.setId(pozo.getId());

        partialUpdatedPozo.numeropozo(UPDATED_NUMEROPOZO);

        restPozoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPozo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPozo))
            )
            .andExpect(status().isOk());

        // Validate the Pozo in the database
        List<Pozo> pozoList = pozoRepository.findAll();
        assertThat(pozoList).hasSize(databaseSizeBeforeUpdate);
        Pozo testPozo = pozoList.get(pozoList.size() - 1);
        assertThat(testPozo.getNumeropozo()).isEqualTo(UPDATED_NUMEROPOZO);
        assertThat(testPozo.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testPozo.getTipopozo()).isEqualTo(DEFAULT_TIPOPOZO);
        assertThat(testPozo.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testPozo.getLongitud()).isEqualTo(DEFAULT_LONGITUD);
        assertThat(testPozo.getLatitud()).isEqualTo(DEFAULT_LATITUD);
    }

    @Test
    @Transactional
    void fullUpdatePozoWithPatch() throws Exception {
        // Initialize the database
        pozoRepository.saveAndFlush(pozo);

        int databaseSizeBeforeUpdate = pozoRepository.findAll().size();

        // Update the pozo using partial update
        Pozo partialUpdatedPozo = new Pozo();
        partialUpdatedPozo.setId(pozo.getId());

        partialUpdatedPozo
            .numeropozo(UPDATED_NUMEROPOZO)
            .direccion(UPDATED_DIRECCION)
            .tipopozo(UPDATED_TIPOPOZO)
            .createdAt(UPDATED_CREATED_AT)
            .longitud(UPDATED_LONGITUD)
            .latitud(UPDATED_LATITUD);

        restPozoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPozo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPozo))
            )
            .andExpect(status().isOk());

        // Validate the Pozo in the database
        List<Pozo> pozoList = pozoRepository.findAll();
        assertThat(pozoList).hasSize(databaseSizeBeforeUpdate);
        Pozo testPozo = pozoList.get(pozoList.size() - 1);
        assertThat(testPozo.getNumeropozo()).isEqualTo(UPDATED_NUMEROPOZO);
        assertThat(testPozo.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testPozo.getTipopozo()).isEqualTo(UPDATED_TIPOPOZO);
        assertThat(testPozo.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPozo.getLongitud()).isEqualTo(UPDATED_LONGITUD);
        assertThat(testPozo.getLatitud()).isEqualTo(UPDATED_LATITUD);
    }

    @Test
    @Transactional
    void patchNonExistingPozo() throws Exception {
        int databaseSizeBeforeUpdate = pozoRepository.findAll().size();
        pozo.setId(count.incrementAndGet());

        // Create the Pozo
        PozoDTO pozoDTO = pozoMapper.toDto(pozo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPozoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pozoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pozoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pozo in the database
        List<Pozo> pozoList = pozoRepository.findAll();
        assertThat(pozoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPozo() throws Exception {
        int databaseSizeBeforeUpdate = pozoRepository.findAll().size();
        pozo.setId(count.incrementAndGet());

        // Create the Pozo
        PozoDTO pozoDTO = pozoMapper.toDto(pozo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPozoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pozoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pozo in the database
        List<Pozo> pozoList = pozoRepository.findAll();
        assertThat(pozoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPozo() throws Exception {
        int databaseSizeBeforeUpdate = pozoRepository.findAll().size();
        pozo.setId(count.incrementAndGet());

        // Create the Pozo
        PozoDTO pozoDTO = pozoMapper.toDto(pozo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPozoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pozoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pozo in the database
        List<Pozo> pozoList = pozoRepository.findAll();
        assertThat(pozoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePozo() throws Exception {
        // Initialize the database
        pozoRepository.saveAndFlush(pozo);

        int databaseSizeBeforeDelete = pozoRepository.findAll().size();

        // Delete the pozo
        restPozoMockMvc
            .perform(delete(ENTITY_API_URL_ID, pozo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pozo> pozoList = pozoRepository.findAll();
        assertThat(pozoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
