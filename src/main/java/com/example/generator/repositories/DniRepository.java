package com.example.generator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.generator.models.Dni;

public interface DniRepository extends JpaRepository<Dni, Integer> {

}
