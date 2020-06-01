package com.example.gustavo_nogueirajean_bauch.repository;

import com.example.gustavo_nogueirajean_bauch.entity.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    
}