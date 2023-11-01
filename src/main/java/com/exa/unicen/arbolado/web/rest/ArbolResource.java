package com.exa.unicen.arbolado.web.rest;

import com.exa.unicen.arbolado.domain.Arbol;
import com.exa.unicen.arbolado.repository.ArbolRepository;
import com.exa.unicen.arbolado.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.exa.unicen.arbolado.domain.Arbol}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ArbolResource {

    private final Logger log = LoggerFactory.getLogger(ArbolResource.class);

    private static final String ENTITY_NAME = "arbol";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArbolRepository arbolRepository;

    public ArbolResource(ArbolRepository arbolRepository) {
        this.arbolRepository = arbolRepository;
    }

    /**
     * {@code POST  /arbols} : Create a new arbol.
     *
     * @param arbol the arbol to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new arbol, or with status {@code 400 (Bad Request)} if the arbol has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/arbols")
    public ResponseEntity<Arbol> createArbol(@RequestBody Arbol arbol) throws URISyntaxException {
        log.debug("REST request to save Arbol : {}", arbol);
        if (arbol.getId() != null) {
            throw new BadRequestAlertException("A new arbol cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Arbol result = arbolRepository.save(arbol);
        return ResponseEntity
            .created(new URI("/api/arbols/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /arbols/:id} : Updates an existing arbol.
     *
     * @param id the id of the arbol to save.
     * @param arbol the arbol to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated arbol,
     * or with status {@code 400 (Bad Request)} if the arbol is not valid,
     * or with status {@code 500 (Internal Server Error)} if the arbol couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/arbols/{id}")
    public ResponseEntity<Arbol> updateArbol(@PathVariable(value = "id", required = false) final Long id, @RequestBody Arbol arbol)
        throws URISyntaxException {
        log.debug("REST request to update Arbol : {}, {}", id, arbol);
        if (arbol.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, arbol.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!arbolRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Arbol result = arbolRepository.save(arbol);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, arbol.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /arbols/:id} : Partial updates given fields of an existing arbol, field will ignore if it is null
     *
     * @param id the id of the arbol to save.
     * @param arbol the arbol to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated arbol,
     * or with status {@code 400 (Bad Request)} if the arbol is not valid,
     * or with status {@code 404 (Not Found)} if the arbol is not found,
     * or with status {@code 500 (Internal Server Error)} if the arbol couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/arbols/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Arbol> partialUpdateArbol(@PathVariable(value = "id", required = false) final Long id, @RequestBody Arbol arbol)
        throws URISyntaxException {
        log.debug("REST request to partial update Arbol partially : {}, {}", id, arbol);
        if (arbol.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, arbol.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!arbolRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Arbol> result = arbolRepository
            .findById(arbol.getId())
            .map(existingArbol -> {
                if (arbol.getAltura() != null) {
                    existingArbol.setAltura(arbol.getAltura());
                }
                if (arbol.getLatitud() != null) {
                    existingArbol.setLatitud(arbol.getLatitud());
                }
                if (arbol.getLongitud() != null) {
                    existingArbol.setLongitud(arbol.getLongitud());
                }
                if (arbol.getCircunferencia() != null) {
                    existingArbol.setCircunferencia(arbol.getCircunferencia());
                }
                if (arbol.getFechaPlantacion() != null) {
                    existingArbol.setFechaPlantacion(arbol.getFechaPlantacion());
                }
                if (arbol.getFechaUltimaPoda() != null) {
                    existingArbol.setFechaUltimaPoda(arbol.getFechaUltimaPoda());
                }
                if (arbol.getDescripcion() != null) {
                    existingArbol.setDescripcion(arbol.getDescripcion());
                }
                if (arbol.getImagen() != null) {
                    existingArbol.setImagen(arbol.getImagen());
                }
                if (arbol.getEstado() != null) {
                    existingArbol.setEstado(arbol.getEstado());
                }
                if (arbol.getTipoFrente() != null) {
                    existingArbol.setTipoFrente(arbol.getTipoFrente());
                }
                if (arbol.getInterferencia() != null) {
                    existingArbol.setInterferencia(arbol.getInterferencia());
                }
                if (arbol.getAlturaCalle() != null) {
                    existingArbol.setAlturaCalle(arbol.getAlturaCalle());
                }
                if (arbol.getExposicionSol() != null) {
                    existingArbol.setExposicionSol(arbol.getExposicionSol());
                }

                return existingArbol;
            })
            .map(arbolRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, arbol.getId().toString())
        );
    }

    /**
     * {@code GET  /arbols} : get all the arbols.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of arbols in body.
     */
    @GetMapping("/arbols")
    public ResponseEntity<List<Arbol>> getAllArbols(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Arbols");
        Page<Arbol> page = arbolRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /arbols/:id} : get the "id" arbol.
     *
     * @param id the id of the arbol to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the arbol, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/arbols/{id}")
    public ResponseEntity<Arbol> getArbol(@PathVariable Long id) {
        log.debug("REST request to get Arbol : {}", id);
        Optional<Arbol> arbol = arbolRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(arbol);
    }

    /**
     * {@code DELETE  /arbols/:id} : delete the "id" arbol.
     *
     * @param id the id of the arbol to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/arbols/{id}")
    public ResponseEntity<Void> deleteArbol(@PathVariable Long id) {
        log.debug("REST request to delete Arbol : {}", id);
        arbolRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
    
    /**
     * Custom Method
     * {@code GET  /arbols/:especieId} : get all arboles with specified specue.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of arbols in body.
     */
    @GetMapping("/arbols/especies/{especieId}")
    public List<Arbol> getArbolsBySpecie(@PathVariable Long especieId) {

        return arbolRepository.findAllByEspecieId(especieId);
        /*HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());*/
    }
}
