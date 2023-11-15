package com.exa.unicen.arbolado.web.rest;

import com.exa.unicen.arbolado.domain.Arbol;
import com.exa.unicen.arbolado.repository.ArbolRepository;
import com.exa.unicen.arbolado.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
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

    @GetMapping("/arbols")
    public ResponseEntity<Map<String, Object>> getAllArbols(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {

        Page<Arbol> page = arbolRepository.findAllByOrderByUpdatedAtDesc(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        Map<String, Object> data = Map.of(
        		"content", page.getContent(), 
        		"total", page.getTotalPages(),
        		"page", page.getNumber()
        );
        return ResponseEntity.ok().headers(headers).body(data);
    }

    @GetMapping("/arbols/{id}")
    public ResponseEntity<Arbol> getArbol(@PathVariable Long id) {
        log.debug("REST request to get Arbol : {}", id);
        Optional<Arbol> arbol = arbolRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(arbol);
    }

    @DeleteMapping("/arbols/{id}")
    public ResponseEntity<Void> deleteArbol(@PathVariable Long id) {
        log.debug("REST request to delete Arbol : {}", id);
        arbolRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
    
    @GetMapping("/arbols/especies/{especieId}")
    public List<Arbol> getArbolsBySpecie(@PathVariable Long especieId) {

        return arbolRepository.findAllByEspecieId(especieId);
    }
    
    @GetMapping("/arbols/last")
    public Arbol getLastInsertedArbol() {

        return arbolRepository.findOneByLastUpdatedAt();
    }
    
    @GetMapping("/arbols/all")
    public List<Arbol> getAllArboles() {

        return arbolRepository.findAll();
    }
}
