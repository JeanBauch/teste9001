package com.example.gustavo_nogueirajean_bauch.controller;

import java.util.List;

import com.example.gustavo_nogueirajean_bauch.entity.Barbeiro;
import com.example.gustavo_nogueirajean_bauch.entity.Especializacao;
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
public class EspecializacaoController {
    
    @Autowired
    private EspecializacaoService es;
    
    @Autowired
    private BarbeiroService bs;

    @GetMapping("/especializacao/listar")
    public ModelAndView getEspecializacaoMV()
    {
        ModelAndView mv = new ModelAndView("EspecializacaoTemplate");
        mv.addObject("especializacao", new Especializacao());
        mv.addObject("especializacoes", es.getEspecializacao());
        return mv;
    }

    @GetMapping("/detalhesEspecializacao/{id}")
    public ModelAndView getEspecializacaoByIdMV(@PathVariable(name = "id") Integer id)
    {
        ModelAndView mv = new ModelAndView("EspecializacaoDetalhesView");
        Especializacao especializacao = es.getEspecializacaoById(id);
        mv.addObject("especializacao", especializacao);

        List<Barbeiro> barbeirosNaoAssociados = bs.getBarbeiro();
        barbeirosNaoAssociados.removeAll(especializacao.getBarbeiros());
        mv.addObject("barbeiros", barbeirosNaoAssociados);

        return mv;
    }

    @PostMapping("/associarBarbeiro")
    public String saveEspecializacaoBarbeiro(@ModelAttribute Barbeiro barbeiro, @RequestParam Integer codE)
    {
        Especializacao especializacao = es.getEspecializacaoById(codE);
        barbeiro = bs.getBarbeiroaById(barbeiro.getIdBarbeiro());

        especializacao.getBarbeiros().add(barbeiro);
        es.addEspecializacao(especializacao);

        return "redirect:/detalhesEspecializacao/" + codE;
    }

    @PostMapping("/salvarEspecializacao")
    public String saveEspecializacao(@ModelAttribute Especializacao e, RedirectAttributes attributes)
    {
        boolean resp = es.addEspecializacao(e);
        if(!resp)
            attributes.addFlashAttribute("erro", "Campo iniciado com SPACE");
        return "redirect:/especializacao/listar";
    }

    @GetMapping("/editarEspecializacao")
    public ModelAndView editarEspecializacao(@RequestParam Integer idB)
    {
        ModelAndView mv = new ModelAndView("EspecializacaoEditView");

        Especializacao especializacao = es.getEspecializacaoById(idB);
        mv.addObject("especializacao", especializacao);
        mv.addObject("barbeiro", bs.getBarbeiro());

        return mv;
    }

    @GetMapping("/removerEspecializacaoBarbeiro/{idE}/{idB}")
    public String removerBarbeiroE(@PathVariable Integer idE, @PathVariable Integer idB)
    {
        Especializacao especializacao = es.getEspecializacaoById(idE);
        Barbeiro barbeiro = bs.getBarbeiroaById(idB);

        especializacao.getBarbeiros().remove(barbeiro);
        es.addEspecializacao(especializacao);

        return "redirect:/editarEspecializacao?idB=" + idE;
    }

    @GetMapping("/removerEspecializacao")
    public String removeBarbeiro(@RequestParam Integer idB, RedirectAttributes attributes)
    {
        Especializacao especializacao = es.getEspecializacaoById(idB);
        boolean resp = es.removeEspecializacao(especializacao);

        if(!resp)
            attributes.addFlashAttribute("erro", "Ainda possui barbeiros cadastrados com essa especialidade");
        return "redirect:/especializacao/listar";
    }
}