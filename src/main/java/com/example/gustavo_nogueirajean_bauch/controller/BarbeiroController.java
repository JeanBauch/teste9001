package com.example.gustavo_nogueirajean_bauch.controller;

import java.util.List;

import com.example.gustavo_nogueirajean_bauch.entity.Agendamento;
import com.example.gustavo_nogueirajean_bauch.entity.Barbeiro;
import com.example.gustavo_nogueirajean_bauch.entity.Especializacao;
import com.example.gustavo_nogueirajean_bauch.service.AgendamentoService;
import com.example.gustavo_nogueirajean_bauch.service.BarbeiroService;
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
public class BarbeiroController {
    @Autowired
    private BarbeiroService beS;


    @Autowired
    private AgendamentoService as;

    @Autowired
    private EspecializacaoService es;

    @GetMapping("/barbeiro/listar")
    public ModelAndView getBarbeiroMV()
    {
        ModelAndView mv = new ModelAndView("BarbeiroTemplate");
        mv.addObject("barbeiro", new Barbeiro());
        mv.addObject("barbeiros", beS.getBarbeiro());
        mv.addObject("agendamentos", as.getAgendamento());
        
        return mv;
    }
    
    @GetMapping("/detalhesBarbeiro/{id}")
    public ModelAndView getBarbeiroByIdMV(@PathVariable(name = "id") Integer id)
    {
        ModelAndView mv = new ModelAndView("BarbeiroDetalhesView");
        Barbeiro barbeiro = beS.getBarbeiroaById(id);
        mv.addObject("barbeiro", barbeiro);
        
        List<Especializacao> especializacaoNaoAssociada =  es.getEspecializacao();
        especializacaoNaoAssociada.removeAll(barbeiro.getEspecializacao());
        mv.addObject("especializacoes", especializacaoNaoAssociada);


        return mv;
    }

    
    @PostMapping("/associarEspecializacao")
    public String saveAutorLivro(@ModelAttribute Especializacao especializacao, @RequestParam Integer codB)
    {
        Barbeiro barbeiro  = beS.getBarbeiroaById(codB);
        especializacao = es.getEspecializacaoById(especializacao.getIdEspecializacao());

        barbeiro.getEspecializacao().add(especializacao);
        beS.addBarbeiro(barbeiro);

        return "redirect:/detalhesBarbeiro/" + codB;
    }

    @PostMapping("/salvarBarbeiro")
    public String saveBarbeiroMV(@ModelAttribute Barbeiro barbeiro, RedirectAttributes attributes)
    {
        boolean resp = beS.addBarbeiro(barbeiro);
        if(!resp)
            attributes.addFlashAttribute("erro", "Campo iniciado com SPACE ou valor negativo");
        return "redirect:/barbeiro/listar";
    }

    @GetMapping("/editarBarbeiro")
    public ModelAndView editarBarbeiro(@RequestParam Integer idB)
    {
        ModelAndView mv = new ModelAndView("BarbeiroEditView");

        Barbeiro barbeiro = beS.getBarbeiroaById(idB);
        mv.addObject("barbeiro", barbeiro);
        mv.addObject("agendamentos", as.getAgendamento());
        
        return mv;
    }

    @GetMapping("/removerBarbeiroAgendamento/{idB}/{idA}")
    public String removerBarbeiroC(@PathVariable Integer idB, @PathVariable Integer idA)
    {
        Barbeiro barbeiro = beS.getBarbeiroaById(idB);
        Agendamento agendamento = as.getAgendamentoById(idA);

        barbeiro.getAgendamento().remove(agendamento);
        beS.addBarbeiro(barbeiro);

        return "redirect:/editarBarbeiro?idB=" + idB;
    }

    @GetMapping("/removerBarbeiroEspecializacao/{idB}/{idE}")
    public String removerBarbeiroE(@PathVariable Integer idB, @PathVariable Integer idE)
    {
        Barbeiro barbeiro = beS.getBarbeiroaById(idB);
        Especializacao especializacao = es.getEspecializacaoById(idE);

        barbeiro.getEspecializacao().remove(especializacao);
        beS.addBarbeiro(barbeiro);

        return "redirect:/editarBarbeiro?idB=" + idB;
    }

    @GetMapping("/removerBarbeiro")
    public String removerBarbeiro(@RequestParam Integer idB, RedirectAttributes attributes)
    {
        Barbeiro barbeiro = beS.getBarbeiroaById(idB);
        boolean resp = beS.removeBarbeiro(barbeiro);
        if(!resp)
            attributes.addFlashAttribute("erro2", "Barbeiro ainda possui agendamentos pendentes");
        return "redirect:/barbeiro/listar";
    }
}