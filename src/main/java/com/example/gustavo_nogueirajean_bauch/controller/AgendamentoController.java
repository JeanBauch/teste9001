package com.example.gustavo_nogueirajean_bauch.controller;

import com.example.gustavo_nogueirajean_bauch.entity.Agendamento;
import com.example.gustavo_nogueirajean_bauch.service.AgendamentoService;
import com.example.gustavo_nogueirajean_bauch.service.BarbeiroService;
import com.example.gustavo_nogueirajean_bauch.service.ClienteService;

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
    private BarbeiroService bs;

    @Autowired
    private ClienteService cs;

    @GetMapping("/agendamento/listar")
    public ModelAndView getAgendamentoMV()
    {
        ModelAndView mv = new ModelAndView("AgendamentoTemplate");
        mv.addObject("agendamento", new Agendamento());
        mv.addObject("agendamentos", as.getAgendamento());
        mv.addObject("barbeiros", bs.getBarbeiro());
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

    @PostMapping("/salvarAgendamento")
    public String saveAgendamentoMV(@ModelAttribute Agendamento agendamento, RedirectAttributes attributes)
    {
        as.addAgendamento(agendamento);
        return "redirect:/agendamento/listar";
    }

    @GetMapping("/removerAgendamento")
    public String removerAutor(@RequestParam Integer idB, RedirectAttributes attributes)
    {
        Agendamento agendamento = as.getAgendamentoById(idB);
        as.removeAgendamento(agendamento);

        return "redirect:/agendamento/listar";
    }

}