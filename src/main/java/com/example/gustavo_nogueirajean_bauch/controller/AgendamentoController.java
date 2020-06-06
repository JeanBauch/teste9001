package com.example.gustavo_nogueirajean_bauch.controller;

import java.util.ArrayList;

import com.example.gustavo_nogueirajean_bauch.entity.Agendamento;
import com.example.gustavo_nogueirajean_bauch.entity.Barbeiro;
import com.example.gustavo_nogueirajean_bauch.service.AgendamentoService;
import com.example.gustavo_nogueirajean_bauch.service.ClienteService;
import com.example.gustavo_nogueirajean_bauch.service.EspecializacaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AgendamentoController {
    @Autowired
    private AgendamentoService as;

    @Autowired
    private EspecializacaoService es;

    @Autowired
    private ClienteService cs;

    @GetMapping("/agendamento/listar")
    public ModelAndView getAgendamentoMV()
    {
        ModelAndView mv = new ModelAndView("AgendamentoTemplate");
        mv.addObject("agendamento", new Agendamento());
        mv.addObject("agendamentos", as.getAgendamento());
        mv.addObject("especializacoes", es.getEspecializacao());
        mv.addObject("clientes", cs.getCliente());
        return mv;
    }

    @GetMapping("/detalhesAgendamento/{id}")
    public ModelAndView getAutorByIdMV(@PathVariable(name="id") Integer id)
    {
        Agendamento agendamento = as.getAgendamentoById(id);
        ModelAndView mv = new ModelAndView("AgendamentoDetalhesView");
        mv.addObject("agendamentos", agendamento);
        return mv;
    }

    @GetMapping("/salvarAgendamentoGet")
    public ModelAndView saveAgendamentoGet()
    {
        ModelAndView mv = new ModelAndView("AgendamentoTemplate2");
        return mv;
    }

    @GetMapping("/agendamento/finalizar/{id}")
    public String finalizarAgendamento(@PathVariable(name="id") Integer id) {
        Agendamento agendamento = as.getAgendamentoById(id);
        as.finalizarAgendamento(agendamento);
        as.update(agendamento);
        System.out.println(agendamento.isFinalizado());
        return "redirect:/agendamento/listar";
    }

    @PostMapping("/salvarAgendamento")
    public String saveAgendamentoMV(@ModelAttribute Agendamento agendamento, RedirectAttributes attributes)
    {
        boolean resp = as.addAgendamento(agendamento);
        if(!resp)
            attributes.addFlashAttribute("erro", "Agendamento inserido fora do tempo de expediente ou o cliente já tem um agendamento nesse horário/próximo a ele");
        return "redirect:/agendamento/listar";
    }

    @PostMapping("/salvarAgendamento2")
    public String saveAgendamento2MV(@ModelAttribute Agendamento agendamento, RedirectAttributes attributes)
    {
        ArrayList<Barbeiro> barbeiroDisp = new ArrayList<>();
        ArrayList<Barbeiro> barbeiroIndisp = new ArrayList<>();
        boolean temp, temp2;
        attributes.addFlashAttribute("agendamento", agendamento);

        int horaAgendamentoNow = agendamento.getHora().getHour()*60;
        horaAgendamentoNow += agendamento.getHora().getMinute();


        for (Barbeiro barbeiro : agendamento.getEspecializacao().getBarbeiros()) {
            if(as.getAgendamento().size() != 0)
            {
                for (Agendamento agendamentos : as.getAgendamento()) {
                    temp = false;
                    temp2 = false;

                    int horaAgendamentoAtual = agendamentos.getHora().getHour()*60;
                    horaAgendamentoAtual += agendamentos.getHora().getMinute();

                    int difTempo = horaAgendamentoNow - horaAgendamentoAtual;
                    
                    if(difTempo < 30 && difTempo > -30)
                        temp = true;
                    
                    String dataNow = agendamento.getData().toString();
                    String datas = agendamentos.getData().toString();


                    if(dataNow.equals(datas))
                        temp2 = true;
                    

                    if(agendamentos.getBarbeiro() == barbeiro && temp2 && temp)
                    {
                        if(!barbeiroIndisp.contains(barbeiro))
                        {
                            while(barbeiroDisp.remove(barbeiro))
                            {

                            }
                            barbeiroIndisp.add(barbeiro);
                        }
                            
                    }
                    else
                    {
                        if(!barbeiroDisp.contains(barbeiro))
                        {
                            if(!barbeiroIndisp.contains(barbeiro))
                            barbeiroDisp.add(barbeiro);
                        }
                            
                    }
                        
                }
            }
            else
            {
                barbeiroDisp.add(barbeiro);
            }
            
        }

        attributes.addFlashAttribute("barbeiroDisp", barbeiroDisp);
        attributes.addFlashAttribute("barbeiroIndisp", barbeiroIndisp);

        return "redirect:/salvarAgendamentoGet";
    }

    @GetMapping("/removerAgendamento")
    public String removerAutor(@RequestParam Integer idB, RedirectAttributes attributes)
    {
        Agendamento agendamento = as.getAgendamentoById(idB);
        as.removeAgendamento(agendamento);

        return "redirect:/agendamento/listar";
    }

}