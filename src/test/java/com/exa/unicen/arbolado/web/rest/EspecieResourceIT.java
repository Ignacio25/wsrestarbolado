package com.exa.unicen.arbolado.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.exa.unicen.arbolado.IntegrationTest;
import com.exa.unicen.arbolado.domain.Especie;
import com.exa.unicen.arbolado.repository.EspecieRepository;
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
 * Integration tests for the {@link EspecieResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EspecieResourceIT {

    private static final String DEFAULT_NOMBRE_CIENTIFICO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_CIENTIFICO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_COMUN = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_COMUN = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGEN = "AAAAAAAAAA";
    private static final String UPDATED_IMAGEN = "BBBBBBBBBB";

    private static final String DEFAULT_ESTADO_CONSERVACION = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO_CONSERVACION = "BBBBBBBBBB";

    private static final Float DEFAULT_ALTURA_MAX = 1F;
    private static final Float UPDATED_ALTURA_MAX = 2F;

    private static final String DEFAULT_FAMILIA = "AAAAAAAAAA";
    private static final String UPDATED_FAMILIA = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGEN = "AAAAAAAAAA";
    private static final String UPDATED_ORIGEN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/especies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EspecieRepository especieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEspecieMockMvc;

    private Especie especie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Especie createEntity(EntityManager em) {
        Especie especie = new Especie()
            .nombreCientifico(DEFAULT_NOMBRE_CIENTIFICO)
            .nombreComun(DEFAULT_NOMBRE_COMUN)
            .descripcion(DEFAULT_DESCRIPCION)
            .imagen(DEFAULT_IMAGEN)
            .estadoConservacion(DEFAULT_ESTADO_CONSERVACION)
            .alturaMax(DEFAULT_ALTURA_MAX)
            .familia(DEFAULT_FAMILIA)
            .origen(DEFAULT_ORIGEN);
        return especie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Especie createUpdatedEntity(EntityManager em) {
        Especie especie = new Especie()
            .nombreCientifico(UPDATED_NOMBRE_CIENTIFICO)
            .nombreComun(UPDATED_NOMBRE_COMUN)
            .descripcion(UPDATED_DESCRIPCION)
            .imagen(UPDATED_IMAGEN)
            .estadoConservacion(UPDATED_ESTADO_CONSERVACION)
            .alturaMax(UPDATED_ALTURA_MAX)
            .familia(UPDATED_FAMILIA)
            .origen(UPDATED_ORIGEN);
        return especie;
    }

    @BeforeEach
    public void initTest() {
        especie = createEntity(em);
    }

    @Test
    @Transactional
    void createEspecie() throws Exception {
        int databaseSizeBeforeCreate = especieRepository.findAll().size();
        // Create the Especie
        restEspecieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(especie)))
            .andExpect(status().isCreated());

        // Validate the Especie in the database
        List<Especie> especieList = especieRepository.findAll();
        assertThat(especieList).hasSize(databaseSizeBeforeCreate + 1);
        Especie testEspecie = especieList.get(especieList.size() - 1);
        assertThat(testEspecie.getNombreCientifico()).isEqualTo(DEFAULT_NOMBRE_CIENTIFICO);
        assertThat(testEspecie.getNombreComun()).isEqualTo(DEFAULT_NOMBRE_COMUN);
        assertThat(testEspecie.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testEspecie.getImagen()).isEqualTo(DEFAULT_IMAGEN);
        assertThat(testEspecie.getEstadoConservacion()).isEqualTo(DEFAULT_ESTADO_CONSERVACION);
        assertThat(testEspecie.getAlturaMax()).isEqualTo(DEFAULT_ALTURA_MAX);
        assertThat(testEspecie.getFamilia()).isEqualTo(DEFAULT_FAMILIA);
        assertThat(testEspecie.getOrigen()).isEqualTo(DEFAULT_ORIGEN);
    }

    @Test
    @Transactional
    void createEspecieWithExistingId() throws Exception {
        // Create the Especie with an existing ID
        especie.setId(1L);

        int databaseSizeBeforeCreate = especieRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEspecieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(especie)))
            .andExpect(status().isBadRequest());

        // Validate the Especie in the database
        List<Especie> especieList = especieRepository.findAll();
        assertThat(especieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreComunIsRequired() throws Exception {
        int databaseSizeBeforeTest = especieRepository.findAll().size();
        // set the field null
        especie.setNombreComun(null);

        // Create the Especie, which fails.

        restEspecieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(especie)))
            .andExpect(status().isBadRequest());

        List<Especie> especieList = especieRepository.findAll();
        assertThat(especieList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEspecies() throws Exception {
        // Initialize the database
        especieRepository.saveAndFlush(especie);

        // Get all the especieList
        restEspecieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(especie.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreCientifico").value(hasItem(DEFAULT_NOMBRE_CIENTIFICO)))
            .andExpect(jsonPath("$.[*].nombreComun").value(hasItem(DEFAULT_NOMBRE_COMUN)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(DEFAULT_IMAGEN)))
            .andExpect(jsonPath("$.[*].estadoConservacion").value(hasItem(DEFAULT_ESTADO_CONSERVACION)))
            .andExpect(jsonPath("$.[*].alturaMax").value(hasItem(DEFAULT_ALTURA_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].familia").value(hasItem(DEFAULT_FAMILIA)))
            .andExpect(jsonPath("$.[*].origen").value(hasItem(DEFAULT_ORIGEN)));
    }

    @Test
    @Transactional
    void getEspecie() throws Exception {
        // Initialize the database
        especieRepository.saveAndFlush(especie);

        // Get the especie
        restEspecieMockMvc
            .perform(get(ENTITY_API_URL_ID, especie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(especie.getId().intValue()))
            .andExpect(jsonPath("$.nombreCientifico").value(DEFAULT_NOMBRE_CIENTIFICO))
            .andExpect(jsonPath("$.nombreComun").value(DEFAULT_NOMBRE_COMUN))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.imagen").value(DEFAULT_IMAGEN))
            .andExpect(jsonPath("$.estadoConservacion").value(DEFAULT_ESTADO_CONSERVACION))
            .andExpect(jsonPath("$.alturaMax").value(DEFAULT_ALTURA_MAX.doubleValue()))
            .andExpect(jsonPath("$.familia").value(DEFAULT_FAMILIA))
            .andExpect(jsonPath("$.origen").value(DEFAULT_ORIGEN));
    }

    @Test
    @Transactional
    void getNonExistingEspecie() throws Exception {
        // Get the especie
        restEspecieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEspecie() throws Exception {
        // Initialize the database
        especieRepository.saveAndFlush(especie);

        int databaseSizeBeforeUpdate = especieRepository.findAll().size();

        // Update the especie
        Especie updatedEspecie = especieRepository.findById(especie.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEspecie are not directly saved in db
        em.detach(updatedEspecie);
        updatedEspecie
            .nombreCientifico(UPDATED_NOMBRE_CIENTIFICO)
            .nombreComun(UPDATED_NOMBRE_COMUN)
            .descripcion(UPDATED_DESCRIPCION)
            .imagen(UPDATED_IMAGEN)
            .estadoConservacion(UPDATED_ESTADO_CONSERVACION)
            .alturaMax(UPDATED_ALTURA_MAX)
            .familia(UPDATED_FAMILIA)
            .origen(UPDATED_ORIGEN);

        restEspecieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEspecie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEspecie))
            )
            .andExpect(status().isOk());

        // Validate the Especie in the database
        List<Especie> especieList = especieRepository.findAll();
        assertThat(especieList).hasSize(databaseSizeBeforeUpdate);
        Especie testEspecie = especieList.get(especieList.size() - 1);
        assertThat(testEspecie.getNombreCientifico()).isEqualTo(UPDATED_NOMBRE_CIENTIFICO);
        assertThat(testEspecie.getNombreComun()).isEqualTo(UPDATED_NOMBRE_COMUN);
        assertThat(testEspecie.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testEspecie.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testEspecie.getEstadoConservacion()).isEqualTo(UPDATED_ESTADO_CONSERVACION);
        assertThat(testEspecie.getAlturaMax()).isEqualTo(UPDATED_ALTURA_MAX);
        assertThat(testEspecie.getFamilia()).isEqualTo(UPDATED_FAMILIA);
        assertThat(testEspecie.getOrigen()).isEqualTo(UPDATED_ORIGEN);
    }

    @Test
    @Transactional
    void putNonExistingEspecie() throws Exception {
        int databaseSizeBeforeUpdate = especieRepository.findAll().size();
        especie.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEspecieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, especie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(especie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Especie in the database
        List<Especie> especieList = especieRepository.findAll();
        assertThat(especieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEspecie() throws Exception {
        int databaseSizeBeforeUpdate = especieRepository.findAll().size();
        especie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEspecieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(especie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Especie in the database
        List<Especie> especieList = especieRepository.findAll();
        assertThat(especieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEspecie() throws Exception {
        int databaseSizeBeforeUpdate = especieRepository.findAll().size();
        especie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEspecieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(especie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Especie in the database
        List<Especie> especieList = especieRepository.findAll();
        assertThat(especieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEspecieWithPatch() throws Exception {
        // Initialize the database
        especieRepository.saveAndFlush(especie);

        int databaseSizeBeforeUpdate = especieRepository.findAll().size();

        // Update the especie using partial update
        Especie partialUpdatedEspecie = new Especie();
        partialUpdatedEspecie.setId(especie.getId());

        partialUpdatedEspecie.nombreCientifico(UPDATED_NOMBRE_CIENTIFICO).descripcion(UPDATED_DESCRIPCION).imagen(UPDATED_IMAGEN);

        restEspecieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEspecie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEspecie))
            )
            .andExpect(status().isOk());

        // Validate the Especie in the database
        List<Especie> especieList = especieRepository.findAll();
        assertThat(especieList).hasSize(databaseSizeBeforeUpdate);
        Especie testEspecie = especieList.get(especieList.size() - 1);
        assertThat(testEspecie.getNombreCientifico()).isEqualTo(UPDATED_NOMBRE_CIENTIFICO);
        assertThat(testEspecie.getNombreComun()).isEqualTo(DEFAULT_NOMBRE_COMUN);
        assertThat(testEspecie.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testEspecie.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testEspecie.getEstadoConservacion()).isEqualTo(DEFAULT_ESTADO_CONSERVACION);
        assertThat(testEspecie.getAlturaMax()).isEqualTo(DEFAULT_ALTURA_MAX);
        assertThat(testEspecie.getFamilia()).isEqualTo(DEFAULT_FAMILIA);
        assertThat(testEspecie.getOrigen()).isEqualTo(DEFAULT_ORIGEN);
    }

    @Test
    @Transactional
    void fullUpdateEspecieWithPatch() throws Exception {
        // Initialize the database
        especieRepository.saveAndFlush(especie);

        int databaseSizeBeforeUpdate = especieRepository.findAll().size();

        // Update the especie using partial update
        Especie partialUpdatedEspecie = new Especie();
        partialUpdatedEspecie.setId(especie.getId());

        partialUpdatedEspecie
            .nombreCientifico(UPDATED_NOMBRE_CIENTIFICO)
            .nombreComun(UPDATED_NOMBRE_COMUN)
            .descripcion(UPDATED_DESCRIPCION)
            .imagen(UPDATED_IMAGEN)
            .estadoConservacion(UPDATED_ESTADO_CONSERVACION)
            .alturaMax(UPDATED_ALTURA_MAX)
            .familia(UPDATED_FAMILIA)
            .origen(UPDATED_ORIGEN);

        restEspecieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEspecie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEspecie))
            )
            .andExpect(status().isOk());

        // Validate the Especie in the database
        List<Especie> especieList = especieRepository.findAll();
        assertThat(especieList).hasSize(databaseSizeBeforeUpdate);
        Especie testEspecie = especieList.get(especieList.size() - 1);
        assertThat(testEspecie.getNombreCientifico()).isEqualTo(UPDATED_NOMBRE_CIENTIFICO);
        assertThat(testEspecie.getNombreComun()).isEqualTo(UPDATED_NOMBRE_COMUN);
        assertThat(testEspecie.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testEspecie.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testEspecie.getEstadoConservacion()).isEqualTo(UPDATED_ESTADO_CONSERVACION);
        assertThat(testEspecie.getAlturaMax()).isEqualTo(UPDATED_ALTURA_MAX);
        assertThat(testEspecie.getFamilia()).isEqualTo(UPDATED_FAMILIA);
        assertThat(testEspecie.getOrigen()).isEqualTo(UPDATED_ORIGEN);
    }

    @Test
    @Transactional
    void patchNonExistingEspecie() throws Exception {
        int databaseSizeBeforeUpdate = especieRepository.findAll().size();
        especie.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEspecieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, especie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(especie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Especie in the database
        List<Especie> especieList = especieRepository.findAll();
        assertThat(especieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEspecie() throws Exception {
        int databaseSizeBeforeUpdate = especieRepository.findAll().size();
        especie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEspecieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(especie))
            )
            .andExpect(status().isBadRequest());

        // Validate the Especie in the database
        List<Especie> especieList = especieRepository.findAll();
        assertThat(especieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEspecie() throws Exception {
        int databaseSizeBeforeUpdate = especieRepository.findAll().size();
        especie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEspecieMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(especie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Especie in the database
        List<Especie> especieList = especieRepository.findAll();
        assertThat(especieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEspecie() throws Exception {
        // Initialize the database
        especieRepository.saveAndFlush(especie);

        int databaseSizeBeforeDelete = especieRepository.findAll().size();

        // Delete the especie
        restEspecieMockMvc
            .perform(delete(ENTITY_API_URL_ID, especie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Especie> especieList = especieRepository.findAll();
        assertThat(especieList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
