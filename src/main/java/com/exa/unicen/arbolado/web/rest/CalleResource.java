package com.exa.unicen.arbolado.web.rest;

import com.exa.unicen.arbolado.domain.Calle;
import com.exa.unicen.arbolado.repository.CalleRepository;
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
 * REST controller for managing {@link com.exa.unicen.arbolado.domain.Calle}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CalleResource {

    private final Logger log = LoggerFactory.getLogger(CalleResource.class);

    private static final String ENTITY_NAME = "calle";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CalleRepository calleRepository;

    public CalleResource(CalleRepository calleRepository) {
        this.calleRepository = calleRepository;
    }

    /**
     * {@code POST  /calles} : Create a new calle.
     *
     * @param calle the calle to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new calle, or with status {@code 400 (Bad Request)} if the calle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/calles")
    public ResponseEntity<Calle> createCalle(@Valid @RequestBody Calle calle) throws URISyntaxException {
        log.debug("REST request to save Calle : {}", calle);
        if (calle.getId() != null) {
            throw new BadRequestAlertException("A new calle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Calle result = calleRepository.save(calle);
        return ResponseEntity
            .created(new URI("/api/calles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /calles/:id} : Updates an existing calle.
     *
     * @param id the id of the calle to save.
     * @param calle the calle to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated calle,
     * or with status {@code 400 (Bad Request)} if the calle is not valid,
     * or with status {@code 500 (Internal Server Error)} if the calle couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/calles/{id}")
    public ResponseEntity<Calle> updateCalle(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Calle calle)
        throws URISyntaxException {
        log.debug("REST request to update Calle : {}, {}", id, calle);
        if (calle.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, calle.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!calleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Calle result = calleRepository.save(calle);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, calle.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /calles/:id} : Partial updates given fields of an existing calle, field will ignore if it is null
     *
     * @param id the id of the calle to save.
     * @param calle the calle to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated calle,
     * or with status {@code 400 (Bad Request)} if the calle is not valid,
     * or with status {@code 404 (Not Found)} if the calle is not found,
     * or with status {@code 500 (Internal Server Error)} if the calle couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/calles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Calle> partialUpdateCalle(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Calle calle
    ) throws URISyntaxException {
        log.debug("REST request to partial update Calle partially : {}, {}", id, calle);
        if (calle.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, calle.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!calleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Calle> result = calleRepository
            .findById(calle.getId())
            .map(existingCalle -> {
                if (calle.getNombre() != null) {
                    existingCalle.setNombre(calle.getNombre());
                }
                if (calle.getDescripcion() != null) {
                    existingCalle.setDescripcion(calle.getDescripcion());
                }

                return existingCalle;
            })
            .map(calleRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, calle.getId().toString())
        );
    }

    /**
     * {@code GET  /calles} : get all the calles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of calles in body.
     */
    @GetMapping("/calles")
    public ResponseEntity<List<Calle>> getAllCalles(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Calles");
        Page<Calle> page = calleRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /calles/:id} : get the "id" calle.
     *
     * @param id the id of the calle to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the calle, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/calles/{id}")
    public ResponseEntity<Calle> getCalle(@PathVariable Long id) {
        log.debug("REST request to get Calle : {}", id);
        Optional<Calle> calle = calleRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(calle);
    }

    /**
     * {@code DELETE  /calles/:id} : delete the "id" calle.
     *
     * @param id the id of the calle to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/calles/{id}")
    public ResponseEntity<Void> deleteCalle(@PathVariable Long id) {
        log.debug("REST request to delete Calle : {}", id);
        calleRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
