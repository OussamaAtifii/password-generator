package com.example.generator.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.generator.models.Password;
import com.example.generator.services.PasswordService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/password")
public class PasswordController {

    public PasswordService passwordService;

    public PasswordController(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    /*
     * Endpoint get para obtener la lista completa de todas las contraseñas
     * almacenadas en el repositorio
     */
    @GetMapping()
    public List<Password> getPasswords() {
        return passwordService.getPasswords();
    }

    /*
     * Endpoint get para generar una contraseña y obtenerla
     */
    @GetMapping(value = "/{length}")
    public ResponseEntity<HashMap<String, Object>> getPassword(@PathVariable int length,
            @RequestParam("lower") boolean lowercase,
            @RequestParam("upper") boolean uppercase,
            @RequestParam("nums") boolean numbers,
            @RequestParam("chars") boolean characters) {
        return passwordService.getPassword(length, lowercase, uppercase, numbers, characters);
    }

    /*
     * Endpoint get para generar una lista de contraseñas y obtenerlas
     */
    @GetMapping("/list/{length}")
    public ResponseEntity<HashMap<String, Object>> getPasswordList(@PathVariable int length,
            @RequestParam("lower") boolean lowercase,
            @RequestParam("upper") boolean uppercase,
            @RequestParam("nums") boolean numbers,
            @RequestParam("chars") boolean characters) {
        return passwordService.getPasswordList(length, lowercase, uppercase, numbers, characters);
    }

    /*
     * Endpoint get para obtener la seguridad de una contraseña
     */
    @GetMapping("/security/{contrasena}")
    public Map<String, Object> getSec(@PathVariable String contrasena) {
        return passwordService.determinarSeguridad(passwordService.getSec(contrasena));
    }
}
