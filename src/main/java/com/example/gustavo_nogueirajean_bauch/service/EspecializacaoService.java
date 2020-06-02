package com.example.gustavo_nogueirajean_bauch.service;

import java.util.List;

import com.example.gustavo_nogueirajean_bauch.entity.Especializacao;
import com.example.gustavo_nogueirajean_bauch.repository.EspecializacaoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EspecializacaoService {
    @Autowired
    private EspecializacaoRepository es;

    public List<Especializacao> getEspecializacao()
    {
        return es.findAll();
    }

    public Especializacao getEspecializacaoById(int id)
    {
        return es.findById(id).get();
    }

    public boolean addEspecializacao(Especializacao e)
    {
        if(e.getNome().charAt(0) != ' ' && e.getDescricao().charAt(0) != ' ' && e.getPreco() > 0)
        {
            es.save(e);
            return true;
        }
        return false;
    }

    public boolean removeEspecializacao(Especializacao e)
    {
        if(e.getBarbeiros().size() == 0 && e.getAgendamentos().size() == 0)
        {
            es.delete(e);
            return true;
        }
        return false;
    } 
}