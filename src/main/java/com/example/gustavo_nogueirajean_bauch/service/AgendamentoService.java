package com.example.gustavo_nogueirajean_bauch.service;

import java.util.List;

import com.example.gustavo_nogueirajean_bauch.entity.Agendamento;
import com.example.gustavo_nogueirajean_bauch.repository.AgendamentoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendamentoService {
    @Autowired
    private AgendamentoRepository ar;

    public List<Agendamento> getAgendamento()
    {
        return ar.findAll();
    }

    public Agendamento getAgendamentoById(int id)
    {
        return ar.findById(id).get();
    }

    public boolean addAgendamento(Agendamento a)
    {
        if(a.getData().charAt(0) != ' ' && a.getHora().charAt(0) != ' ')
        {
            ar.save(a);
            return true;
        }
        return false;
    }

    public void removeAgendamento(Agendamento a)
    {
        ar.delete(a);
    }
}