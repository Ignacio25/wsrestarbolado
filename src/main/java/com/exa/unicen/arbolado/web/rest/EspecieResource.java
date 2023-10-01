package com.exa.unicen.arbolado.web.rest;

import com.exa.unicen.arbolado.domain.Especie;
import com.exa.unicen.arbolado.repository.EspecieRepository;
import com.exa.unicen.arbolado.service.CSVService;
import com.exa.unicen.arbolado.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.exa.unicen.arbolado.domain.Especie}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EspecieResource {

    @Autowired
    CSVService fileService;

    private final Logger log = LoggerFactory.getLogger(EspecieResource.class);

    private static final String ENTITY_NAME = "especie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EspecieRepository especieRepository;

    public EspecieResource(EspecieRepository especieRepository) {
        this.especieRepository = especieRepository;
    }

    /**
     * {@code POST  /especies} : Create a new especie.
     *
     * @param especie the especie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new especie, or with status {@code 400 (Bad Request)} if the especie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/especies")
    public ResponseEntity<Especie> createEspecie(@Valid @RequestBody Especie especie) throws URISyntaxException {
        log.debug("REST request to save Especie : {}", especie);
        if (especie.getId() != null) {
            throw new BadRequestAlertException("A new especie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Especie result = especieRepository.save(especie);
        return ResponseEntity
            .created(new URI("/api/especies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /especies/:id} : Updates an existing especie.
     *
     * @param id the id of the especie to save.
     * @param especie the especie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated especie,
     * or with status {@code 400 (Bad Request)} if the especie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the especie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/especies/{id}")
    public ResponseEntity<Especie> updateEspecie(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Especie especie
    ) throws URISyntaxException {
        log.debug("REST request to update Especie : {}, {}", id, especie);
        if (especie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, especie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!especieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Especie result = especieRepository.save(especie);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, especie.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /especies/:id} : Partial updates given fields of an existing especie, field will ignore if it is null
     *
     * @param id the id of the especie to save.
     * @param especie the especie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated especie,
     * or with status {@code 400 (Bad Request)} if the especie is not valid,
     * or with status {@code 404 (Not Found)} if the especie is not found,
     * or with status {@code 500 (Internal Server Error)} if the especie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/especies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Especie> partialUpdateEspecie(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Especie especie
    ) throws URISyntaxException {
        log.debug("REST request to partial update Especie partially : {}, {}", id, especie);
        if (especie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, especie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!especieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Especie> result = especieRepository
            .findById(especie.getId())
            .map(existingEspecie -> {
                if (especie.getNombreCientifico() != null) {
                    existingEspecie.setNombreCientifico(especie.getNombreCientifico());
                }
                if (especie.getNombreComun() != null) {
                    existingEspecie.setNombreComun(especie.getNombreComun());
                }
                if (especie.getDescripcion() != null) {
                    existingEspecie.setDescripcion(especie.getDescripcion());
                }
                if (especie.getImagen() != null) {
                    existingEspecie.setImagen(especie.getImagen());
                }
                if (especie.getEstadoConservacion() != null) {
                    existingEspecie.setEstadoConservacion(especie.getEstadoConservacion());
                }
                if (especie.getAlturaMax() != null) {
                    existingEspecie.setAlturaMax(especie.getAlturaMax());
                }
                if (especie.getFamilia() != null) {
                    existingEspecie.setFamilia(especie.getFamilia());
                }
                if (especie.getOrigen() != null) {
                    existingEspecie.setOrigen(especie.getOrigen());
                }

                return existingEspecie;
            })
            .map(especieRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, especie.getId().toString())
        );
    }

    /**
     * {@code GET  /especies} : get all the especies.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of especies in body.
     */
    @GetMapping("/especies")
    public ResponseEntity<List<Especie>> getAllEspecies(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        Page<Especie> page = especieRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /especies/:id} : get the "id" especie.
     *
     * @param id the id of the especie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the especie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/especies/{id}")
    public ResponseEntity<Especie> getEspecie(@PathVariable Long id) {
        log.debug("REST request to get Especie : {}", id);
        Optional<Especie> especie = especieRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(especie);
    }

    /**
     * {@code DELETE  /especies/:id} : delete the "id" especie.
     *
     * @param id the id of the especie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/especies/{id}")
    public ResponseEntity<Void> deleteEspecie(@PathVariable Long id) {
        log.debug("REST request to delete Especie : {}", id);
        especieRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> getFile() {
        String filename = "arboles.csv";
        InputStreamResource file = new InputStreamResource(fileService.load());

        return ResponseEntity
            .ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
            .contentType(MediaType.parseMediaType("application/csv"))
            .body(file);
    }
}
