package com.municipio.sistemasadm.web.rest;

import static com.municipio.sistemasadm.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.municipio.sistemasadm.IntegrationTest;
import com.municipio.sistemasadm.domain.Infraestructura;
import com.municipio.sistemasadm.domain.Pozo;
import com.municipio.sistemasadm.domain.Proveedor;
import com.municipio.sistemasadm.domain.enumeration.Tipo;
import com.municipio.sistemasadm.repository.InfraestructuraRepository;
import com.municipio.sistemasadm.service.InfraestructuraService;
import com.municipio.sistemasadm.service.dto.InfraestructuraDTO;
import com.municipio.sistemasadm.service.mapper.InfraestructuraMapper;
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
 * Integration tests for the {@link InfraestructuraResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class InfraestructuraResourceIT {

    private static final Tipo DEFAULT_TIPO = Tipo.MANGA;
    private static final Tipo UPDATED_TIPO = Tipo.NAP;

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/infraestructuras";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InfraestructuraRepository infraestructuraRepository;

    @Mock
    private InfraestructuraRepository infraestructuraRepositoryMock;

    @Autowired
    private InfraestructuraMapper infraestructuraMapper;

    @Mock
    private InfraestructuraService infraestructuraServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInfraestructuraMockMvc;

    private Infraestructura infraestructura;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Infraestructura createEntity(EntityManager em) {
        Infraestructura infraestructura = new Infraestructura().tipo(DEFAULT_TIPO).createdAt(DEFAULT_CREATED_AT);
        // Add required entity
        Proveedor proveedor;
        if (TestUtil.findAll(em, Proveedor.class).isEmpty()) {
            proveedor = ProveedorResourceIT.createEntity(em);
            em.persist(proveedor);
            em.flush();
        } else {
            proveedor = TestUtil.findAll(em, Proveedor.class).get(0);
        }
        infraestructura.setRazonSocial(proveedor);
        // Add required entity
        Pozo pozo;
        if (TestUtil.findAll(em, Pozo.class).isEmpty()) {
            pozo = PozoResourceIT.createEntity(em);
            em.persist(pozo);
            em.flush();
        } else {
            pozo = TestUtil.findAll(em, Pozo.class).get(0);
        }
        infraestructura.getNumeropozos().add(pozo);
        return infraestructura;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Infraestructura createUpdatedEntity(EntityManager em) {
        Infraestructura infraestructura = new Infraestructura().tipo(UPDATED_TIPO).createdAt(UPDATED_CREATED_AT);
        // Add required entity
        Proveedor proveedor;
        if (TestUtil.findAll(em, Proveedor.class).isEmpty()) {
            proveedor = ProveedorResourceIT.createUpdatedEntity(em);
            em.persist(proveedor);
            em.flush();
        } else {
            proveedor = TestUtil.findAll(em, Proveedor.class).get(0);
        }
        infraestructura.setRazonSocial(proveedor);
        // Add required entity
        Pozo pozo;
        if (TestUtil.findAll(em, Pozo.class).isEmpty()) {
            pozo = PozoResourceIT.createUpdatedEntity(em);
            em.persist(pozo);
            em.flush();
        } else {
            pozo = TestUtil.findAll(em, Pozo.class).get(0);
        }
        infraestructura.getNumeropozos().add(pozo);
        return infraestructura;
    }

    @BeforeEach
    public void initTest() {
        infraestructura = createEntity(em);
    }

    @Test
    @Transactional
    void createInfraestructura() throws Exception {
        int databaseSizeBeforeCreate = infraestructuraRepository.findAll().size();
        // Create the Infraestructura
        InfraestructuraDTO infraestructuraDTO = infraestructuraMapper.toDto(infraestructura);
        restInfraestructuraMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(infraestructuraDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Infraestructura in the database
        List<Infraestructura> infraestructuraList = infraestructuraRepository.findAll();
        assertThat(infraestructuraList).hasSize(databaseSizeBeforeCreate + 1);
        Infraestructura testInfraestructura = infraestructuraList.get(infraestructuraList.size() - 1);
        assertThat(testInfraestructura.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testInfraestructura.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    @Transactional
    void createInfraestructuraWithExistingId() throws Exception {
        // Create the Infraestructura with an existing ID
        infraestructura.setId(1L);
        InfraestructuraDTO infraestructuraDTO = infraestructuraMapper.toDto(infraestructura);

        int databaseSizeBeforeCreate = infraestructuraRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInfraestructuraMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(infraestructuraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Infraestructura in the database
        List<Infraestructura> infraestructuraList = infraestructuraRepository.findAll();
        assertThat(infraestructuraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = infraestructuraRepository.findAll().size();
        // set the field null
        infraestructura.setCreatedAt(null);

        // Create the Infraestructura, which fails.
        InfraestructuraDTO infraestructuraDTO = infraestructuraMapper.toDto(infraestructura);

        restInfraestructuraMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(infraestructuraDTO))
            )
            .andExpect(status().isBadRequest());

        List<Infraestructura> infraestructuraList = infraestructuraRepository.findAll();
        assertThat(infraestructuraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllInfraestructuras() throws Exception {
        // Initialize the database
        infraestructuraRepository.saveAndFlush(infraestructura);

        // Get all the infraestructuraList
        restInfraestructuraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(infraestructura.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllInfraestructurasWithEagerRelationshipsIsEnabled() throws Exception {
        when(infraestructuraServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restInfraestructuraMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(infraestructuraServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllInfraestructurasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(infraestructuraServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restInfraestructuraMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(infraestructuraRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getInfraestructura() throws Exception {
        // Initialize the database
        infraestructuraRepository.saveAndFlush(infraestructura);

        // Get the infraestructura
        restInfraestructuraMockMvc
            .perform(get(ENTITY_API_URL_ID, infraestructura.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(infraestructura.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)));
    }

    @Test
    @Transactional
    void getNonExistingInfraestructura() throws Exception {
        // Get the infraestructura
        restInfraestructuraMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingInfraestructura() throws Exception {
        // Initialize the database
        infraestructuraRepository.saveAndFlush(infraestructura);

        int databaseSizeBeforeUpdate = infraestructuraRepository.findAll().size();

        // Update the infraestructura
        Infraestructura updatedInfraestructura = infraestructuraRepository.findById(infraestructura.getId()).get();
        // Disconnect from session so that the updates on updatedInfraestructura are not directly saved in db
        em.detach(updatedInfraestructura);
        updatedInfraestructura.tipo(UPDATED_TIPO).createdAt(UPDATED_CREATED_AT);
        InfraestructuraDTO infraestructuraDTO = infraestructuraMapper.toDto(updatedInfraestructura);

        restInfraestructuraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, infraestructuraDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(infraestructuraDTO))
            )
            .andExpect(status().isOk());

        // Validate the Infraestructura in the database
        List<Infraestructura> infraestructuraList = infraestructuraRepository.findAll();
        assertThat(infraestructuraList).hasSize(databaseSizeBeforeUpdate);
        Infraestructura testInfraestructura = infraestructuraList.get(infraestructuraList.size() - 1);
        assertThat(testInfraestructura.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testInfraestructura.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingInfraestructura() throws Exception {
        int databaseSizeBeforeUpdate = infraestructuraRepository.findAll().size();
        infraestructura.setId(count.incrementAndGet());

        // Create the Infraestructura
        InfraestructuraDTO infraestructuraDTO = infraestructuraMapper.toDto(infraestructura);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInfraestructuraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, infraestructuraDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(infraestructuraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Infraestructura in the database
        List<Infraestructura> infraestructuraList = infraestructuraRepository.findAll();
        assertThat(infraestructuraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInfraestructura() throws Exception {
        int databaseSizeBeforeUpdate = infraestructuraRepository.findAll().size();
        infraestructura.setId(count.incrementAndGet());

        // Create the Infraestructura
        InfraestructuraDTO infraestructuraDTO = infraestructuraMapper.toDto(infraestructura);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInfraestructuraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(infraestructuraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Infraestructura in the database
        List<Infraestructura> infraestructuraList = infraestructuraRepository.findAll();
        assertThat(infraestructuraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInfraestructura() throws Exception {
        int databaseSizeBeforeUpdate = infraestructuraRepository.findAll().size();
        infraestructura.setId(count.incrementAndGet());

        // Create the Infraestructura
        InfraestructuraDTO infraestructuraDTO = infraestructuraMapper.toDto(infraestructura);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInfraestructuraMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(infraestructuraDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Infraestructura in the database
        List<Infraestructura> infraestructuraList = infraestructuraRepository.findAll();
        assertThat(infraestructuraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInfraestructuraWithPatch() throws Exception {
        // Initialize the database
        infraestructuraRepository.saveAndFlush(infraestructura);

        int databaseSizeBeforeUpdate = infraestructuraRepository.findAll().size();

        // Update the infraestructura using partial update
        Infraestructura partialUpdatedInfraestructura = new Infraestructura();
        partialUpdatedInfraestructura.setId(infraestructura.getId());

        restInfraestructuraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInfraestructura.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInfraestructura))
            )
            .andExpect(status().isOk());

        // Validate the Infraestructura in the database
        List<Infraestructura> infraestructuraList = infraestructuraRepository.findAll();
        assertThat(infraestructuraList).hasSize(databaseSizeBeforeUpdate);
        Infraestructura testInfraestructura = infraestructuraList.get(infraestructuraList.size() - 1);
        assertThat(testInfraestructura.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testInfraestructura.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateInfraestructuraWithPatch() throws Exception {
        // Initialize the database
        infraestructuraRepository.saveAndFlush(infraestructura);

        int databaseSizeBeforeUpdate = infraestructuraRepository.findAll().size();

        // Update the infraestructura using partial update
        Infraestructura partialUpdatedInfraestructura = new Infraestructura();
        partialUpdatedInfraestructura.setId(infraestructura.getId());

        partialUpdatedInfraestructura.tipo(UPDATED_TIPO).createdAt(UPDATED_CREATED_AT);

        restInfraestructuraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInfraestructura.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInfraestructura))
            )
            .andExpect(status().isOk());

        // Validate the Infraestructura in the database
        List<Infraestructura> infraestructuraList = infraestructuraRepository.findAll();
        assertThat(infraestructuraList).hasSize(databaseSizeBeforeUpdate);
        Infraestructura testInfraestructura = infraestructuraList.get(infraestructuraList.size() - 1);
        assertThat(testInfraestructura.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testInfraestructura.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingInfraestructura() throws Exception {
        int databaseSizeBeforeUpdate = infraestructuraRepository.findAll().size();
        infraestructura.setId(count.incrementAndGet());

        // Create the Infraestructura
        InfraestructuraDTO infraestructuraDTO = infraestructuraMapper.toDto(infraestructura);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInfraestructuraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, infraestructuraDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(infraestructuraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Infraestructura in the database
        List<Infraestructura> infraestructuraList = infraestructuraRepository.findAll();
        assertThat(infraestructuraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInfraestructura() throws Exception {
        int databaseSizeBeforeUpdate = infraestructuraRepository.findAll().size();
        infraestructura.setId(count.incrementAndGet());

        // Create the Infraestructura
        InfraestructuraDTO infraestructuraDTO = infraestructuraMapper.toDto(infraestructura);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInfraestructuraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(infraestructuraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Infraestructura in the database
        List<Infraestructura> infraestructuraList = infraestructuraRepository.findAll();
        assertThat(infraestructuraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInfraestructura() throws Exception {
        int databaseSizeBeforeUpdate = infraestructuraRepository.findAll().size();
        infraestructura.setId(count.incrementAndGet());

        // Create the Infraestructura
        InfraestructuraDTO infraestructuraDTO = infraestructuraMapper.toDto(infraestructura);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInfraestructuraMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(infraestructuraDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Infraestructura in the database
        List<Infraestructura> infraestructuraList = infraestructuraRepository.findAll();
        assertThat(infraestructuraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInfraestructura() throws Exception {
        // Initialize the database
        infraestructuraRepository.saveAndFlush(infraestructura);

        int databaseSizeBeforeDelete = infraestructuraRepository.findAll().size();

        // Delete the infraestructura
        restInfraestructuraMockMvc
            .perform(delete(ENTITY_API_URL_ID, infraestructura.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Infraestructura> infraestructuraList = infraestructuraRepository.findAll();
        assertThat(infraestructuraList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
