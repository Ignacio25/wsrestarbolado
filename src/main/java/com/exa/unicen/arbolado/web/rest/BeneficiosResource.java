package com.exa.unicen.arbolado.web.rest;

import com.exa.unicen.arbolado.domain.Arbol;
import com.exa.unicen.arbolado.domain.Especie;
import com.exa.unicen.arbolado.repository.ArbolRepository;
import com.exa.unicen.arbolado.repository.EspecieRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * BeneficiosResource controller
 */
@RestController
@RequestMapping("/api")
public class BeneficiosResource {

    private final ArbolRepository arbolRepository;
    private final EspecieRepository especieRepository;

    public BeneficiosResource(ArbolRepository arbolRepository, EspecieRepository especieRepository) {
        this.arbolRepository = arbolRepository;
        this.especieRepository = especieRepository;
    }

    @PostMapping("/stormwater")
    public Object calculateStormwater(@RequestBody Object request) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        final String uri = "http://127.0.0.1:8080/stormwater";
        long id = 8;
        Optional<Especie> especie = especieRepository.findById(id);

        if (!especie.isPresent()) return null;

        String nombre = especie.get().getNombreComun();
        String[] nombreSplitted = nombre.split(" ");
        nombreSplitted[0] = StringUtils.stripAccents(nombreSplitted[0]);
        nombreSplitted[1] = StringUtils.stripAccents(nombreSplitted[1]);

        List<Arbol> arboles = arbolRepository.findAllByEspecieId(id);
        Map<String, Object> data = new HashMap<>();
        data.put("dbh_cm", 0);
        data.put("region", "InlEmpCLM");
        data.put("genus", nombreSplitted[0]);
        data.put("species", nombreSplitted[1]);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        List<Object> resultado = new ArrayList<>();
        for (Arbol arbol : arboles) {
            data.put("dbh_cm", arbol.getCircunferencia());
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(data);
            HttpEntity<String> entity = new HttpEntity<>(json, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(uri, entity, String.class);
            Map<String, Object> map = mapper.readValue(response.getBody(), Map.class);
            try {
                Map<String, Object> data3 = new HashMap<>();
                data3.put("arbol", arbol.getId());
                data3.put("stormwater", map.get("stormwater"));
                resultado.add(data3);
            } catch (Exception e) {}
        }
        return resultado;
    }
}
