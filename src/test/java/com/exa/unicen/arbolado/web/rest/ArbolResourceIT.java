package com.exa.unicen.arbolado.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.exa.unicen.arbolado.IntegrationTest;
import com.exa.unicen.arbolado.domain.Arbol;
import com.exa.unicen.arbolado.repository.ArbolRepository;
import jakarta.persistence.EntityManager;
import java.util.Date;
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
 * Integration tests for the {@link ArbolResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ArbolResourceIT {

    private static final Float DEFAULT_ALTURA = 1F;
    private static final Float UPDATED_ALTURA = 2F;

    private static final String DEFAULT_LATITUD = "AAAAAAAAAA";
    private static final String UPDATED_LATITUD = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUD = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUD = "BBBBBBBBBB";

    private static final Float DEFAULT_CIRCUNFERENCIA = 1F;
    private static final Float UPDATED_CIRCUNFERENCIA = 2F;

    private static final Date DEFAULT_FECHA_PLANTACION = new Date();
    private static final Date UPDATED_FECHA_PLANTACION = new Date();

    private static final Date DEFAULT_FECHA_ULTIMA_PODA = new Date();
    private static final Date UPDATED_FECHA_ULTIMA_PODA = new Date();

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGEN = "AAAAAAAAAA";
    private static final String UPDATED_IMAGEN = "BBBBBBBBBB";

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_FRENTE = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_FRENTE = "BBBBBBBBBB";

    private static final String DEFAULT_INTERFERENCIA = "AAAAAAAAAA";
    private static final String UPDATED_INTERFERENCIA = "BBBBBBBBBB";

    private static final Integer DEFAULT_ALTURA_CALLE = 1;
    private static final Integer UPDATED_ALTURA_CALLE = 2;

    private static final String DEFAULT_EXPOSICION_SOL = "AAAAAAAAAA";
    private static final String UPDATED_EXPOSICION_SOL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/arbols";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ArbolRepository arbolRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArbolMockMvc;

    private Arbol arbol;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Arbol createEntity(EntityManager em) {
        Arbol arbol = new Arbol()
            .altura(DEFAULT_ALTURA)
            .latitud(DEFAULT_LATITUD)
            .longitud(DEFAULT_LONGITUD)
            .circunferencia(DEFAULT_CIRCUNFERENCIA)
            .fechaPlantacion(DEFAULT_FECHA_PLANTACION)
            .fechaUltimaPoda(DEFAULT_FECHA_ULTIMA_PODA)
            .descripcion(DEFAULT_DESCRIPCION)
            .imagen(DEFAULT_IMAGEN)
            .estado(DEFAULT_ESTADO)
            .tipoFrente(DEFAULT_TIPO_FRENTE)
            .interferencia(DEFAULT_INTERFERENCIA)
            .alturaCalle(DEFAULT_ALTURA_CALLE)
            .exposicionSol(DEFAULT_EXPOSICION_SOL);
        return arbol;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Arbol createUpdatedEntity(EntityManager em) {
        Arbol arbol = new Arbol()
            .altura(UPDATED_ALTURA)
            .latitud(UPDATED_LATITUD)
            .longitud(UPDATED_LONGITUD)
            .circunferencia(UPDATED_CIRCUNFERENCIA)
            .fechaPlantacion(UPDATED_FECHA_PLANTACION)
            .fechaUltimaPoda(UPDATED_FECHA_ULTIMA_PODA)
            .descripcion(UPDATED_DESCRIPCION)
            .imagen(UPDATED_IMAGEN)
            .estado(UPDATED_ESTADO)
            .tipoFrente(UPDATED_TIPO_FRENTE)
            .interferencia(UPDATED_INTERFERENCIA)
            .alturaCalle(UPDATED_ALTURA_CALLE)
            .exposicionSol(UPDATED_EXPOSICION_SOL);
        return arbol;
    }

    @BeforeEach
    public void initTest() {
        arbol = createEntity(em);
    }

    @Test
    @Transactional
    void createArbol() throws Exception {
        int databaseSizeBeforeCreate = arbolRepository.findAll().size();
        // Create the Arbol
        restArbolMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(arbol)))
            .andExpect(status().isCreated());

        // Validate the Arbol in the database
        List<Arbol> arbolList = arbolRepository.findAll();
        assertThat(arbolList).hasSize(databaseSizeBeforeCreate + 1);
        Arbol testArbol = arbolList.get(arbolList.size() - 1);
        assertThat(testArbol.getAltura()).isEqualTo(DEFAULT_ALTURA);
        assertThat(testArbol.getLatitud()).isEqualTo(DEFAULT_LATITUD);
        assertThat(testArbol.getLongitud()).isEqualTo(DEFAULT_LONGITUD);
        assertThat(testArbol.getCircunferencia()).isEqualTo(DEFAULT_CIRCUNFERENCIA);
        assertThat(testArbol.getFechaPlantacion()).isEqualTo(DEFAULT_FECHA_PLANTACION);
        assertThat(testArbol.getFechaUltimaPoda()).isEqualTo(DEFAULT_FECHA_ULTIMA_PODA);
        assertThat(testArbol.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testArbol.getImagen()).isEqualTo(DEFAULT_IMAGEN);
        assertThat(testArbol.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testArbol.getTipoFrente()).isEqualTo(DEFAULT_TIPO_FRENTE);
        assertThat(testArbol.getInterferencia()).isEqualTo(DEFAULT_INTERFERENCIA);
        assertThat(testArbol.getAlturaCalle()).isEqualTo(DEFAULT_ALTURA_CALLE);
        assertThat(testArbol.getExposicionSol()).isEqualTo(DEFAULT_EXPOSICION_SOL);
    }

    @Test
    @Transactional
    void createArbolWithExistingId() throws Exception {
        // Create the Arbol with an existing ID
        arbol.setId(1L);

        int databaseSizeBeforeCreate = arbolRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArbolMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(arbol)))
            .andExpect(status().isBadRequest());

        // Validate the Arbol in the database
        List<Arbol> arbolList = arbolRepository.findAll();
        assertThat(arbolList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllArbols() throws Exception {
        // Initialize the database
        arbolRepository.saveAndFlush(arbol);

        // Get all the arbolList
        restArbolMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(arbol.getId().intValue())))
            .andExpect(jsonPath("$.[*].altura").value(hasItem(DEFAULT_ALTURA.doubleValue())))
            .andExpect(jsonPath("$.[*].latitud").value(hasItem(DEFAULT_LATITUD)))
            .andExpect(jsonPath("$.[*].longitud").value(hasItem(DEFAULT_LONGITUD)))
            .andExpect(jsonPath("$.[*].circunferencia").value(hasItem(DEFAULT_CIRCUNFERENCIA.doubleValue())))
            .andExpect(jsonPath("$.[*].fechaPlantacion").value(hasItem(DEFAULT_FECHA_PLANTACION.toString())))
            .andExpect(jsonPath("$.[*].fechaUltimaPoda").value(hasItem(DEFAULT_FECHA_ULTIMA_PODA.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(DEFAULT_IMAGEN)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].tipoFrente").value(hasItem(DEFAULT_TIPO_FRENTE)))
            .andExpect(jsonPath("$.[*].interferencia").value(hasItem(DEFAULT_INTERFERENCIA)))
            .andExpect(jsonPath("$.[*].alturaCalle").value(hasItem(DEFAULT_ALTURA_CALLE)))
            .andExpect(jsonPath("$.[*].exposicionSol").value(hasItem(DEFAULT_EXPOSICION_SOL)));
    }

    @Test
    @Transactional
    void getArbol() throws Exception {
        // Initialize the database
        arbolRepository.saveAndFlush(arbol);

        // Get the arbol
        restArbolMockMvc
            .perform(get(ENTITY_API_URL_ID, arbol.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(arbol.getId().intValue()))
            .andExpect(jsonPath("$.altura").value(DEFAULT_ALTURA.doubleValue()))
            .andExpect(jsonPath("$.latitud").value(DEFAULT_LATITUD))
            .andExpect(jsonPath("$.longitud").value(DEFAULT_LONGITUD))
            .andExpect(jsonPath("$.circunferencia").value(DEFAULT_CIRCUNFERENCIA.doubleValue()))
            .andExpect(jsonPath("$.fechaPlantacion").value(DEFAULT_FECHA_PLANTACION.toString()))
            .andExpect(jsonPath("$.fechaUltimaPoda").value(DEFAULT_FECHA_ULTIMA_PODA.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.imagen").value(DEFAULT_IMAGEN))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO))
            .andExpect(jsonPath("$.tipoFrente").value(DEFAULT_TIPO_FRENTE))
            .andExpect(jsonPath("$.interferencia").value(DEFAULT_INTERFERENCIA))
            .andExpect(jsonPath("$.alturaCalle").value(DEFAULT_ALTURA_CALLE))
            .andExpect(jsonPath("$.exposicionSol").value(DEFAULT_EXPOSICION_SOL));
    }

    @Test
    @Transactional
    void getNonExistingArbol() throws Exception {
        // Get the arbol
        restArbolMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingArbol() throws Exception {
        // Initialize the database
        arbolRepository.saveAndFlush(arbol);

        int databaseSizeBeforeUpdate = arbolRepository.findAll().size();

        // Update the arbol
        Arbol updatedArbol = arbolRepository.findById(arbol.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedArbol are not directly saved in db
        em.detach(updatedArbol);
        updatedArbol
            .altura(UPDATED_ALTURA)
            .latitud(UPDATED_LATITUD)
            .longitud(UPDATED_LONGITUD)
            .circunferencia(UPDATED_CIRCUNFERENCIA)
            .fechaPlantacion(UPDATED_FECHA_PLANTACION)
            .fechaUltimaPoda(UPDATED_FECHA_ULTIMA_PODA)
            .descripcion(UPDATED_DESCRIPCION)
            .imagen(UPDATED_IMAGEN)
            .estado(UPDATED_ESTADO)
            .tipoFrente(UPDATED_TIPO_FRENTE)
            .interferencia(UPDATED_INTERFERENCIA)
            .alturaCalle(UPDATED_ALTURA_CALLE)
            .exposicionSol(UPDATED_EXPOSICION_SOL);

        restArbolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedArbol.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedArbol))
            )
            .andExpect(status().isOk());

        // Validate the Arbol in the database
        List<Arbol> arbolList = arbolRepository.findAll();
        assertThat(arbolList).hasSize(databaseSizeBeforeUpdate);
        Arbol testArbol = arbolList.get(arbolList.size() - 1);
        assertThat(testArbol.getAltura()).isEqualTo(UPDATED_ALTURA);
        assertThat(testArbol.getLatitud()).isEqualTo(UPDATED_LATITUD);
        assertThat(testArbol.getLongitud()).isEqualTo(UPDATED_LONGITUD);
        assertThat(testArbol.getCircunferencia()).isEqualTo(UPDATED_CIRCUNFERENCIA);
        assertThat(testArbol.getFechaPlantacion()).isEqualTo(UPDATED_FECHA_PLANTACION);
        assertThat(testArbol.getFechaUltimaPoda()).isEqualTo(UPDATED_FECHA_ULTIMA_PODA);
        assertThat(testArbol.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testArbol.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testArbol.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testArbol.getTipoFrente()).isEqualTo(UPDATED_TIPO_FRENTE);
        assertThat(testArbol.getInterferencia()).isEqualTo(UPDATED_INTERFERENCIA);
        assertThat(testArbol.getAlturaCalle()).isEqualTo(UPDATED_ALTURA_CALLE);
        assertThat(testArbol.getExposicionSol()).isEqualTo(UPDATED_EXPOSICION_SOL);
    }

    @Test
    @Transactional
    void putNonExistingArbol() throws Exception {
        int databaseSizeBeforeUpdate = arbolRepository.findAll().size();
        arbol.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArbolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, arbol.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(arbol))
            )
            .andExpect(status().isBadRequest());

        // Validate the Arbol in the database
        List<Arbol> arbolList = arbolRepository.findAll();
        assertThat(arbolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArbol() throws Exception {
        int databaseSizeBeforeUpdate = arbolRepository.findAll().size();
        arbol.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArbolMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(arbol))
            )
            .andExpect(status().isBadRequest());

        // Validate the Arbol in the database
        List<Arbol> arbolList = arbolRepository.findAll();
        assertThat(arbolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArbol() throws Exception {
        int databaseSizeBeforeUpdate = arbolRepository.findAll().size();
        arbol.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArbolMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(arbol)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Arbol in the database
        List<Arbol> arbolList = arbolRepository.findAll();
        assertThat(arbolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArbolWithPatch() throws Exception {
        // Initialize the database
        arbolRepository.saveAndFlush(arbol);

        int databaseSizeBeforeUpdate = arbolRepository.findAll().size();

        // Update the arbol using partial update
        Arbol partialUpdatedArbol = new Arbol();
        partialUpdatedArbol.setId(arbol.getId());

        partialUpdatedArbol.altura(UPDATED_ALTURA).fechaPlantacion(UPDATED_FECHA_PLANTACION).tipoFrente(UPDATED_TIPO_FRENTE);

        restArbolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArbol.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArbol))
            )
            .andExpect(status().isOk());

        // Validate the Arbol in the database
        List<Arbol> arbolList = arbolRepository.findAll();
        assertThat(arbolList).hasSize(databaseSizeBeforeUpdate);
        Arbol testArbol = arbolList.get(arbolList.size() - 1);
        assertThat(testArbol.getAltura()).isEqualTo(UPDATED_ALTURA);
        assertThat(testArbol.getLatitud()).isEqualTo(DEFAULT_LATITUD);
        assertThat(testArbol.getLongitud()).isEqualTo(DEFAULT_LONGITUD);
        assertThat(testArbol.getCircunferencia()).isEqualTo(DEFAULT_CIRCUNFERENCIA);
        assertThat(testArbol.getFechaPlantacion()).isEqualTo(UPDATED_FECHA_PLANTACION);
        assertThat(testArbol.getFechaUltimaPoda()).isEqualTo(DEFAULT_FECHA_ULTIMA_PODA);
        assertThat(testArbol.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testArbol.getImagen()).isEqualTo(DEFAULT_IMAGEN);
        assertThat(testArbol.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testArbol.getTipoFrente()).isEqualTo(UPDATED_TIPO_FRENTE);
        assertThat(testArbol.getInterferencia()).isEqualTo(DEFAULT_INTERFERENCIA);
        assertThat(testArbol.getAlturaCalle()).isEqualTo(DEFAULT_ALTURA_CALLE);
        assertThat(testArbol.getExposicionSol()).isEqualTo(DEFAULT_EXPOSICION_SOL);
    }

    @Test
    @Transactional
    void fullUpdateArbolWithPatch() throws Exception {
        // Initialize the database
        arbolRepository.saveAndFlush(arbol);

        int databaseSizeBeforeUpdate = arbolRepository.findAll().size();

        // Update the arbol using partial update
        Arbol partialUpdatedArbol = new Arbol();
        partialUpdatedArbol.setId(arbol.getId());

        partialUpdatedArbol
            .altura(UPDATED_ALTURA)
            .latitud(UPDATED_LATITUD)
            .longitud(UPDATED_LONGITUD)
            .circunferencia(UPDATED_CIRCUNFERENCIA)
            .fechaPlantacion(UPDATED_FECHA_PLANTACION)
            .fechaUltimaPoda(UPDATED_FECHA_ULTIMA_PODA)
            .descripcion(UPDATED_DESCRIPCION)
            .imagen(UPDATED_IMAGEN)
            .estado(UPDATED_ESTADO)
            .tipoFrente(UPDATED_TIPO_FRENTE)
            .interferencia(UPDATED_INTERFERENCIA)
            .alturaCalle(UPDATED_ALTURA_CALLE)
            .exposicionSol(UPDATED_EXPOSICION_SOL);

        restArbolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArbol.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArbol))
            )
            .andExpect(status().isOk());

        // Validate the Arbol in the database
        List<Arbol> arbolList = arbolRepository.findAll();
        assertThat(arbolList).hasSize(databaseSizeBeforeUpdate);
        Arbol testArbol = arbolList.get(arbolList.size() - 1);
        assertThat(testArbol.getAltura()).isEqualTo(UPDATED_ALTURA);
        assertThat(testArbol.getLatitud()).isEqualTo(UPDATED_LATITUD);
        assertThat(testArbol.getLongitud()).isEqualTo(UPDATED_LONGITUD);
        assertThat(testArbol.getCircunferencia()).isEqualTo(UPDATED_CIRCUNFERENCIA);
        assertThat(testArbol.getFechaPlantacion()).isEqualTo(UPDATED_FECHA_PLANTACION);
        assertThat(testArbol.getFechaUltimaPoda()).isEqualTo(UPDATED_FECHA_ULTIMA_PODA);
        assertThat(testArbol.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testArbol.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testArbol.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testArbol.getTipoFrente()).isEqualTo(UPDATED_TIPO_FRENTE);
        assertThat(testArbol.getInterferencia()).isEqualTo(UPDATED_INTERFERENCIA);
        assertThat(testArbol.getAlturaCalle()).isEqualTo(UPDATED_ALTURA_CALLE);
        assertThat(testArbol.getExposicionSol()).isEqualTo(UPDATED_EXPOSICION_SOL);
    }

    @Test
    @Transactional
    void patchNonExistingArbol() throws Exception {
        int databaseSizeBeforeUpdate = arbolRepository.findAll().size();
        arbol.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArbolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, arbol.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(arbol))
            )
            .andExpect(status().isBadRequest());

        // Validate the Arbol in the database
        List<Arbol> arbolList = arbolRepository.findAll();
        assertThat(arbolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArbol() throws Exception {
        int databaseSizeBeforeUpdate = arbolRepository.findAll().size();
        arbol.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArbolMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(arbol))
            )
            .andExpect(status().isBadRequest());

        // Validate the Arbol in the database
        List<Arbol> arbolList = arbolRepository.findAll();
        assertThat(arbolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArbol() throws Exception {
        int databaseSizeBeforeUpdate = arbolRepository.findAll().size();
        arbol.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArbolMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(arbol)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Arbol in the database
        List<Arbol> arbolList = arbolRepository.findAll();
        assertThat(arbolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArbol() throws Exception {
        // Initialize the database
        arbolRepository.saveAndFlush(arbol);

        int databaseSizeBeforeDelete = arbolRepository.findAll().size();

        // Delete the arbol
        restArbolMockMvc
            .perform(delete(ENTITY_API_URL_ID, arbol.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Arbol> arbolList = arbolRepository.findAll();
        assertThat(arbolList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
