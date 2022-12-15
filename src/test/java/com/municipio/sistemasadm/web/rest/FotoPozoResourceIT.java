package com.municipio.sistemasadm.web.rest;

import static com.municipio.sistemasadm.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.municipio.sistemasadm.IntegrationTest;
import com.municipio.sistemasadm.domain.FotoPozo;
import com.municipio.sistemasadm.domain.Pozo;
import com.municipio.sistemasadm.repository.FotoPozoRepository;
import com.municipio.sistemasadm.service.FotoPozoService;
import com.municipio.sistemasadm.service.dto.FotoPozoDTO;
import com.municipio.sistemasadm.service.mapper.FotoPozoMapper;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link FotoPozoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FotoPozoResourceIT {

    private static final byte[] DEFAULT_FOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/foto-pozos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FotoPozoRepository fotoPozoRepository;

    @Mock
    private FotoPozoRepository fotoPozoRepositoryMock;

    @Autowired
    private FotoPozoMapper fotoPozoMapper;

    @Mock
    private FotoPozoService fotoPozoServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFotoPozoMockMvc;

    private FotoPozo fotoPozo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FotoPozo createEntity(EntityManager em) {
        FotoPozo fotoPozo = new FotoPozo()
            .foto(DEFAULT_FOTO)
            .fotoContentType(DEFAULT_FOTO_CONTENT_TYPE)
            .descripcion(DEFAULT_DESCRIPCION)
            .createdAt(DEFAULT_CREATED_AT);
        // Add required entity
        Pozo pozo;
        if (TestUtil.findAll(em, Pozo.class).isEmpty()) {
            pozo = PozoResourceIT.createEntity(em);
            em.persist(pozo);
            em.flush();
        } else {
            pozo = TestUtil.findAll(em, Pozo.class).get(0);
        }
        fotoPozo.setNumeropozo(pozo);
        return fotoPozo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FotoPozo createUpdatedEntity(EntityManager em) {
        FotoPozo fotoPozo = new FotoPozo()
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE)
            .descripcion(UPDATED_DESCRIPCION)
            .createdAt(UPDATED_CREATED_AT);
        // Add required entity
        Pozo pozo;
        if (TestUtil.findAll(em, Pozo.class).isEmpty()) {
            pozo = PozoResourceIT.createUpdatedEntity(em);
            em.persist(pozo);
            em.flush();
        } else {
            pozo = TestUtil.findAll(em, Pozo.class).get(0);
        }
        fotoPozo.setNumeropozo(pozo);
        return fotoPozo;
    }

    @BeforeEach
    public void initTest() {
        fotoPozo = createEntity(em);
    }

    @Test
    @Transactional
    void createFotoPozo() throws Exception {
        int databaseSizeBeforeCreate = fotoPozoRepository.findAll().size();
        // Create the FotoPozo
        FotoPozoDTO fotoPozoDTO = fotoPozoMapper.toDto(fotoPozo);
        restFotoPozoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fotoPozoDTO)))
            .andExpect(status().isCreated());

        // Validate the FotoPozo in the database
        List<FotoPozo> fotoPozoList = fotoPozoRepository.findAll();
        assertThat(fotoPozoList).hasSize(databaseSizeBeforeCreate + 1);
        FotoPozo testFotoPozo = fotoPozoList.get(fotoPozoList.size() - 1);
        assertThat(testFotoPozo.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testFotoPozo.getFotoContentType()).isEqualTo(DEFAULT_FOTO_CONTENT_TYPE);
        assertThat(testFotoPozo.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testFotoPozo.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    @Transactional
    void createFotoPozoWithExistingId() throws Exception {
        // Create the FotoPozo with an existing ID
        fotoPozo.setId(1L);
        FotoPozoDTO fotoPozoDTO = fotoPozoMapper.toDto(fotoPozo);

        int databaseSizeBeforeCreate = fotoPozoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFotoPozoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fotoPozoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FotoPozo in the database
        List<FotoPozo> fotoPozoList = fotoPozoRepository.findAll();
        assertThat(fotoPozoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = fotoPozoRepository.findAll().size();
        // set the field null
        fotoPozo.setDescripcion(null);

        // Create the FotoPozo, which fails.
        FotoPozoDTO fotoPozoDTO = fotoPozoMapper.toDto(fotoPozo);

        restFotoPozoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fotoPozoDTO)))
            .andExpect(status().isBadRequest());

        List<FotoPozo> fotoPozoList = fotoPozoRepository.findAll();
        assertThat(fotoPozoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = fotoPozoRepository.findAll().size();
        // set the field null
        fotoPozo.setCreatedAt(null);

        // Create the FotoPozo, which fails.
        FotoPozoDTO fotoPozoDTO = fotoPozoMapper.toDto(fotoPozo);

        restFotoPozoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fotoPozoDTO)))
            .andExpect(status().isBadRequest());

        List<FotoPozo> fotoPozoList = fotoPozoRepository.findAll();
        assertThat(fotoPozoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFotoPozos() throws Exception {
        // Initialize the database
        fotoPozoRepository.saveAndFlush(fotoPozo);

        // Get all the fotoPozoList
        restFotoPozoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fotoPozo.getId().intValue())))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFotoPozosWithEagerRelationshipsIsEnabled() throws Exception {
        when(fotoPozoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFotoPozoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(fotoPozoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFotoPozosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(fotoPozoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFotoPozoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(fotoPozoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getFotoPozo() throws Exception {
        // Initialize the database
        fotoPozoRepository.saveAndFlush(fotoPozo);

        // Get the fotoPozo
        restFotoPozoMockMvc
            .perform(get(ENTITY_API_URL_ID, fotoPozo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fotoPozo.getId().intValue()))
            .andExpect(jsonPath("$.fotoContentType").value(DEFAULT_FOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto").value(Base64Utils.encodeToString(DEFAULT_FOTO)))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)));
    }

    @Test
    @Transactional
    void getNonExistingFotoPozo() throws Exception {
        // Get the fotoPozo
        restFotoPozoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFotoPozo() throws Exception {
        // Initialize the database
        fotoPozoRepository.saveAndFlush(fotoPozo);

        int databaseSizeBeforeUpdate = fotoPozoRepository.findAll().size();

        // Update the fotoPozo
        FotoPozo updatedFotoPozo = fotoPozoRepository.findById(fotoPozo.getId()).get();
        // Disconnect from session so that the updates on updatedFotoPozo are not directly saved in db
        em.detach(updatedFotoPozo);
        updatedFotoPozo
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE)
            .descripcion(UPDATED_DESCRIPCION)
            .createdAt(UPDATED_CREATED_AT);
        FotoPozoDTO fotoPozoDTO = fotoPozoMapper.toDto(updatedFotoPozo);

        restFotoPozoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fotoPozoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fotoPozoDTO))
            )
            .andExpect(status().isOk());

        // Validate the FotoPozo in the database
        List<FotoPozo> fotoPozoList = fotoPozoRepository.findAll();
        assertThat(fotoPozoList).hasSize(databaseSizeBeforeUpdate);
        FotoPozo testFotoPozo = fotoPozoList.get(fotoPozoList.size() - 1);
        assertThat(testFotoPozo.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testFotoPozo.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
        assertThat(testFotoPozo.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testFotoPozo.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingFotoPozo() throws Exception {
        int databaseSizeBeforeUpdate = fotoPozoRepository.findAll().size();
        fotoPozo.setId(count.incrementAndGet());

        // Create the FotoPozo
        FotoPozoDTO fotoPozoDTO = fotoPozoMapper.toDto(fotoPozo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFotoPozoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fotoPozoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fotoPozoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FotoPozo in the database
        List<FotoPozo> fotoPozoList = fotoPozoRepository.findAll();
        assertThat(fotoPozoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFotoPozo() throws Exception {
        int databaseSizeBeforeUpdate = fotoPozoRepository.findAll().size();
        fotoPozo.setId(count.incrementAndGet());

        // Create the FotoPozo
        FotoPozoDTO fotoPozoDTO = fotoPozoMapper.toDto(fotoPozo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFotoPozoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fotoPozoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FotoPozo in the database
        List<FotoPozo> fotoPozoList = fotoPozoRepository.findAll();
        assertThat(fotoPozoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFotoPozo() throws Exception {
        int databaseSizeBeforeUpdate = fotoPozoRepository.findAll().size();
        fotoPozo.setId(count.incrementAndGet());

        // Create the FotoPozo
        FotoPozoDTO fotoPozoDTO = fotoPozoMapper.toDto(fotoPozo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFotoPozoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fotoPozoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FotoPozo in the database
        List<FotoPozo> fotoPozoList = fotoPozoRepository.findAll();
        assertThat(fotoPozoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFotoPozoWithPatch() throws Exception {
        // Initialize the database
        fotoPozoRepository.saveAndFlush(fotoPozo);

        int databaseSizeBeforeUpdate = fotoPozoRepository.findAll().size();

        // Update the fotoPozo using partial update
        FotoPozo partialUpdatedFotoPozo = new FotoPozo();
        partialUpdatedFotoPozo.setId(fotoPozo.getId());

        partialUpdatedFotoPozo.descripcion(UPDATED_DESCRIPCION).createdAt(UPDATED_CREATED_AT);

        restFotoPozoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFotoPozo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFotoPozo))
            )
            .andExpect(status().isOk());

        // Validate the FotoPozo in the database
        List<FotoPozo> fotoPozoList = fotoPozoRepository.findAll();
        assertThat(fotoPozoList).hasSize(databaseSizeBeforeUpdate);
        FotoPozo testFotoPozo = fotoPozoList.get(fotoPozoList.size() - 1);
        assertThat(testFotoPozo.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testFotoPozo.getFotoContentType()).isEqualTo(DEFAULT_FOTO_CONTENT_TYPE);
        assertThat(testFotoPozo.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testFotoPozo.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateFotoPozoWithPatch() throws Exception {
        // Initialize the database
        fotoPozoRepository.saveAndFlush(fotoPozo);

        int databaseSizeBeforeUpdate = fotoPozoRepository.findAll().size();

        // Update the fotoPozo using partial update
        FotoPozo partialUpdatedFotoPozo = new FotoPozo();
        partialUpdatedFotoPozo.setId(fotoPozo.getId());

        partialUpdatedFotoPozo
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE)
            .descripcion(UPDATED_DESCRIPCION)
            .createdAt(UPDATED_CREATED_AT);

        restFotoPozoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFotoPozo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFotoPozo))
            )
            .andExpect(status().isOk());

        // Validate the FotoPozo in the database
        List<FotoPozo> fotoPozoList = fotoPozoRepository.findAll();
        assertThat(fotoPozoList).hasSize(databaseSizeBeforeUpdate);
        FotoPozo testFotoPozo = fotoPozoList.get(fotoPozoList.size() - 1);
        assertThat(testFotoPozo.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testFotoPozo.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
        assertThat(testFotoPozo.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testFotoPozo.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingFotoPozo() throws Exception {
        int databaseSizeBeforeUpdate = fotoPozoRepository.findAll().size();
        fotoPozo.setId(count.incrementAndGet());

        // Create the FotoPozo
        FotoPozoDTO fotoPozoDTO = fotoPozoMapper.toDto(fotoPozo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFotoPozoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fotoPozoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fotoPozoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FotoPozo in the database
        List<FotoPozo> fotoPozoList = fotoPozoRepository.findAll();
        assertThat(fotoPozoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFotoPozo() throws Exception {
        int databaseSizeBeforeUpdate = fotoPozoRepository.findAll().size();
        fotoPozo.setId(count.incrementAndGet());

        // Create the FotoPozo
        FotoPozoDTO fotoPozoDTO = fotoPozoMapper.toDto(fotoPozo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFotoPozoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fotoPozoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FotoPozo in the database
        List<FotoPozo> fotoPozoList = fotoPozoRepository.findAll();
        assertThat(fotoPozoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFotoPozo() throws Exception {
        int databaseSizeBeforeUpdate = fotoPozoRepository.findAll().size();
        fotoPozo.setId(count.incrementAndGet());

        // Create the FotoPozo
        FotoPozoDTO fotoPozoDTO = fotoPozoMapper.toDto(fotoPozo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFotoPozoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fotoPozoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FotoPozo in the database
        List<FotoPozo> fotoPozoList = fotoPozoRepository.findAll();
        assertThat(fotoPozoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFotoPozo() throws Exception {
        // Initialize the database
        fotoPozoRepository.saveAndFlush(fotoPozo);

        int databaseSizeBeforeDelete = fotoPozoRepository.findAll().size();

        // Delete the fotoPozo
        restFotoPozoMockMvc
            .perform(delete(ENTITY_API_URL_ID, fotoPozo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FotoPozo> fotoPozoList = fotoPozoRepository.findAll();
        assertThat(fotoPozoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
