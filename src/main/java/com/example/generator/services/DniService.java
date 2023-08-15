package com.example.generator.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.generator.repositories.DniRepository;

@Service
public class DniService {

    public DniRepository dniRepository;

    public DniService(DniRepository dniRepository) {
        this.dniRepository = dniRepository;
    }

    public ResponseEntity<Map<String, Object>> validarDni(String dni) {
        Map<String, Object> data = new HashMap<>();
        String letrasValidas = "TRWAGMYFPDXBNJZSQVHLCKE";

        try {
            char letraDni = letrasValidas.charAt(Integer.parseInt(dni.substring(0, 8)) % 23);
            char letra = Character.toUpperCase(dni.charAt(8));

            if (letra != letraDni || dni.isBlank()) {
                data.put("valid", false);
            } else {
                data.put("valid", true);
            }
        } catch (StringIndexOutOfBoundsException e) {
            data.put("validation", false);
            data.put("error", "Incorrect length");
            return new ResponseEntity<>(data, HttpStatus.OK);
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
