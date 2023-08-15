package com.example.generator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.generator.models.Password;
import java.util.Optional;

public interface PasswordRepository extends JpaRepository<Password, Integer> {

    /**
     * Busca una contraseña en la base de datos y devuelve un Optional que puede
     * contener la contraseña encontrada.
     *
     * @param password La contraseña que se desea buscar en la base de datos.
     * @return Un Optional que puede contener la contraseña encontrada, si existe.
     */
    Optional<Password> findByPassword(String password);

}
