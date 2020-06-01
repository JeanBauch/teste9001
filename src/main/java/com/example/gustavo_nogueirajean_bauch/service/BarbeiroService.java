package com.example.gustavo_nogueirajean_bauch.service;

import java.util.List;

import com.example.gustavo_nogueirajean_bauch.entity.Barbeiro;
import com.example.gustavo_nogueirajean_bauch.repository.BarbeiroRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BarbeiroService {
    @Autowired
    private BarbeiroRepository  beR;

    public List<Barbeiro> getBarbeiro()
    {
        return beR.findAll();
    }

    public Barbeiro getBarbeiroaById(int id)
    {
        return beR.findById(id).get();
    }

    public boolean addBarbeiro(Barbeiro b)
    {
        if(b.getNome().charAt(0) != ' ' && b.getTelefone().charAt(0) != ' ' && b.getSalario() >= 0 && b.getIdade() > 0)
        {
            beR.save(b);
            return true;
        }
        return false;
    }

    public boolean removeBarbeiro(Barbeiro b)
    {
        if(b.getClientes().size() == 0)
        {
            beR.delete(b);
            return true;
        }
        return false;
    }
}