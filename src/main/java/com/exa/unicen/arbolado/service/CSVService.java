package com.exa.unicen.arbolado.service;

import com.exa.unicen.arbolado.domain.Arbol;
import com.exa.unicen.arbolado.repository.ArbolRepository;
import helpers.CSVHelper;
import java.io.ByteArrayInputStream;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CSVService {

    @Autowired
    ArbolRepository repository;

    public ByteArrayInputStream load() {
        List<Arbol> arboles = repository.findAll();

        ByteArrayInputStream in = CSVHelper.tutorialsToCSV(arboles);
        return in;
    }
}
