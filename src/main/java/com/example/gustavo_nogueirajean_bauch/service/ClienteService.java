package com.example.gustavo_nogueirajean_bauch.service;

import java.util.List;

import com.example.gustavo_nogueirajean_bauch.entity.Cliente;
import com.example.gustavo_nogueirajean_bauch.repository.ClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository cr;

    public List<Cliente> getCliente()
    {
        return cr.findAll();
    }

    public Cliente getClienteById(int id)
    {
        return cr.findById(id).get();
    }

    public boolean addCliente(Cliente c)
    {
        if(c.getNome().charAt(0) != ' ' && c.getCpf().charAt(0) != ' ' && c.getIdade() > 0 && c.getTelefone().charAt(0) != ' ')
        {
            cr.save(c);
            return true;
        }
        return false;
    }

    public boolean removeCliente(Cliente c)
    {
        if(c.getAgendamento().size() == 0)
        {
            cr.delete(c);
            return true;
        }
        return false;
    }           
}