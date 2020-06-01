package com.example.gustavo_nogueirajean_bauch.service;

import java.util.List;

import com.example.gustavo_nogueirajean_bauch.entity.Barbearia;
import com.example.gustavo_nogueirajean_bauch.repository.BarbeariaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BarbeariaService {
    @Autowired
    private BarbeariaRepository baR;

    public List<Barbearia> getBarbearia()
    {
        return baR.findAll();
    }

    public Barbearia getBarbeariaById(int id)
    {
        return baR.findById(id).get();
    }

    public boolean addBarbearia(Barbearia b)
    {
        if(b.getNome().charAt(0) != ' ' && b.getEndereco().charAt(0) != ' ' && b.getTelefone().charAt(0) != ' '
        && b.getCnpj().charAt(0) != ' ')
        {
            baR.save(b);
            return true;
        }
        return false;
    }

    public boolean removeBarbearia(Barbearia b)
    {
        if(b.getFuncionarios().size() == 0)
        {
            baR.delete(b);
            return true;
        }
        return false;
    }
}