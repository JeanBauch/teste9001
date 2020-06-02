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
        int horaAgendamentoNow = a.getHora().getHour()*60;
        horaAgendamentoNow += a.getHora().getMinute();
        if(horaAgendamentoNow < 540 || horaAgendamentoNow > 1080)
            return false;
        for (Agendamento ag : ar.findAll()) {
            if(a.getCliente() == ag.getCliente())
            {
                

                int horaAgendamentoAtual = ag.getHora().getHour()*60;
                    horaAgendamentoAtual += ag.getHora().getMinute();

                int difTempo = horaAgendamentoNow - horaAgendamentoAtual;
                String dataNow = ag.getData().toString();
                String datas = a.getData().toString();

                if((difTempo > -30 && difTempo <30) && dataNow.equals(datas))
                    return false;
            }
                        
        }
        ar.save(a);
        return true;
    }

    public void removeAgendamento(Agendamento a)
    {
        ar.delete(a);
    }
}