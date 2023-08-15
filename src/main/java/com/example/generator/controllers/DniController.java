package com.example.generator.controllers;

import java.util.Map;

import com.example.generator.services.DniService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/dni")
public class DniController {

    public DniService dniService;

    public DniController(DniService dniService) {
        this.dniService = dniService;
    }

    /*
     * En proceso
     */
    @GetMapping()
    public String obtenerDni() {
        return "11223344B";
    }

    @GetMapping("/{dniValidar}")
    public ResponseEntity<Map<String, Object>> validarDni(@PathVariable String dniValidar) {
        return dniService.validarDni(dniValidar);
    }

}
