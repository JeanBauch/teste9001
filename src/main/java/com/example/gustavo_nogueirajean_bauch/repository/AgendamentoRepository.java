package com.example.gustavo_nogueirajean_bauch.repository;

import com.example.gustavo_nogueirajean_bauch.entity.Agendamento;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Integer>{
    
}