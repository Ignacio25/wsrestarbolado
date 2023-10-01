package com.exa.unicen.arbolado.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.exa.unicen.arbolado.IntegrationTest;
import com.exa.unicen.arbolado.domain.Calle;
import com.exa.unicen.arbolado.repository.CalleRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CalleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CalleResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/calles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CalleRepository calleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCalleMockMvc;

    private Calle calle;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Calle createEntity(EntityManager em) {
        Calle calle = new Calle().nombre(DEFAULT_NOMBRE).descripcion(DEFAULT_DESCRIPCION);
        return calle;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Calle createUpdatedEntity(EntityManager em) {
        Calle calle = new Calle().nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);
        return calle;
    }

    @BeforeEach
    public void initTest() {
        calle = createEntity(em);
    }

    @Test
    @Transactional
    void createCalle() throws Exception {
        int databaseSizeBeforeCreate = calleRepository.findAll().size();
        // Create the Calle
        restCalleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(calle)))
            .andExpect(status().isCreated());

        // Validate the Calle in the database
        List<Calle> calleList = calleRepository.findAll();
        assertThat(calleList).hasSize(databaseSizeBeforeCreate + 1);
        Calle testCalle = calleList.get(calleList.size() - 1);
        assertThat(testCalle.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testCalle.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    void createCalleWithExistingId() throws Exception {
        // Create the Calle with an existing ID
        calle.setId(1L);

        int databaseSizeBeforeCreate = calleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCalleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(calle)))
            .andExpect(status().isBadRequest());

        // Validate the Calle in the database
        List<Calle> calleList = calleRepository.findAll();
        assertThat(calleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = calleRepository.findAll().size();
        // set the field null
        calle.setNombre(null);

        // Create the Calle, which fails.

        restCalleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(calle)))
            .andExpect(status().isBadRequest());

        List<Calle> calleList = calleRepository.findAll();
        assertThat(calleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = calleRepository.findAll().size();
        // set the field null
        calle.setDescripcion(null);

        // Create the Calle, which fails.

        restCalleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(calle)))
            .andExpect(status().isBadRequest());

        List<Calle> calleList = calleRepository.findAll();
        assertThat(calleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCalles() throws Exception {
        // Initialize the database
        calleRepository.saveAndFlush(calle);

        // Get all the calleList
        restCalleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calle.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }

    @Test
    @Transactional
    void getCalle() throws Exception {
        // Initialize the database
        calleRepository.saveAndFlush(calle);

        // Get the calle
        restCalleMockMvc
            .perform(get(ENTITY_API_URL_ID, calle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(calle.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }

    @Test
    @Transactional
    void getNonExistingCalle() throws Exception {
        // Get the calle
        restCalleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCalle() throws Exception {
        // Initialize the database
        calleRepository.saveAndFlush(calle);

        int databaseSizeBeforeUpdate = calleRepository.findAll().size();

        // Update the calle
        Calle updatedCalle = calleRepository.findById(calle.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCalle are not directly saved in db
        em.detach(updatedCalle);
        updatedCalle.nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);

        restCalleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCalle.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCalle))
            )
            .andExpect(status().isOk());

        // Validate the Calle in the database
        List<Calle> calleList = calleRepository.findAll();
        assertThat(calleList).hasSize(databaseSizeBeforeUpdate);
        Calle testCalle = calleList.get(calleList.size() - 1);
        assertThat(testCalle.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testCalle.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void putNonExistingCalle() throws Exception {
        int databaseSizeBeforeUpdate = calleRepository.findAll().size();
        calle.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCalleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, calle.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(calle))
            )
            .andExpect(status().isBadRequest());

        // Validate the Calle in the database
        List<Calle> calleList = calleRepository.findAll();
        assertThat(calleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCalle() throws Exception {
        int databaseSizeBeforeUpdate = calleRepository.findAll().size();
        calle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCalleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(calle))
            )
            .andExpect(status().isBadRequest());

        // Validate the Calle in the database
        List<Calle> calleList = calleRepository.findAll();
        assertThat(calleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCalle() throws Exception {
        int databaseSizeBeforeUpdate = calleRepository.findAll().size();
        calle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCalleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(calle)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Calle in the database
        List<Calle> calleList = calleRepository.findAll();
        assertThat(calleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCalleWithPatch() throws Exception {
        // Initialize the database
        calleRepository.saveAndFlush(calle);

        int databaseSizeBeforeUpdate = calleRepository.findAll().size();

        // Update the calle using partial update
        Calle partialUpdatedCalle = new Calle();
        partialUpdatedCalle.setId(calle.getId());

        partialUpdatedCalle.nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);

        restCalleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCalle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCalle))
            )
            .andExpect(status().isOk());

        // Validate the Calle in the database
        List<Calle> calleList = calleRepository.findAll();
        assertThat(calleList).hasSize(databaseSizeBeforeUpdate);
        Calle testCalle = calleList.get(calleList.size() - 1);
        assertThat(testCalle.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testCalle.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void fullUpdateCalleWithPatch() throws Exception {
        // Initialize the database
        calleRepository.saveAndFlush(calle);

        int databaseSizeBeforeUpdate = calleRepository.findAll().size();

        // Update the calle using partial update
        Calle partialUpdatedCalle = new Calle();
        partialUpdatedCalle.setId(calle.getId());

        partialUpdatedCalle.nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);

        restCalleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCalle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCalle))
            )
            .andExpect(status().isOk());

        // Validate the Calle in the database
        List<Calle> calleList = calleRepository.findAll();
        assertThat(calleList).hasSize(databaseSizeBeforeUpdate);
        Calle testCalle = calleList.get(calleList.size() - 1);
        assertThat(testCalle.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testCalle.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void patchNonExistingCalle() throws Exception {
        int databaseSizeBeforeUpdate = calleRepository.findAll().size();
        calle.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCalleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, calle.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(calle))
            )
            .andExpect(status().isBadRequest());

        // Validate the Calle in the database
        List<Calle> calleList = calleRepository.findAll();
        assertThat(calleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCalle() throws Exception {
        int databaseSizeBeforeUpdate = calleRepository.findAll().size();
        calle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCalleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(calle))
            )
            .andExpect(status().isBadRequest());

        // Validate the Calle in the database
        List<Calle> calleList = calleRepository.findAll();
        assertThat(calleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCalle() throws Exception {
        int databaseSizeBeforeUpdate = calleRepository.findAll().size();
        calle.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCalleMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(calle)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Calle in the database
        List<Calle> calleList = calleRepository.findAll();
        assertThat(calleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCalle() throws Exception {
        // Initialize the database
        calleRepository.saveAndFlush(calle);

        int databaseSizeBeforeDelete = calleRepository.findAll().size();

        // Delete the calle
        restCalleMockMvc
            .perform(delete(ENTITY_API_URL_ID, calle.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Calle> calleList = calleRepository.findAll();
        assertThat(calleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
