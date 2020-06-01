package com.example.gustavo_nogueirajean_bauch.controller;

import java.util.List;

import com.example.gustavo_nogueirajean_bauch.entity.Barbearia;
import com.example.gustavo_nogueirajean_bauch.entity.Barbeiro;
import com.example.gustavo_nogueirajean_bauch.service.BarbeariaService;
import com.example.gustavo_nogueirajean_bauch.service.BarbeiroService;

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
public class BarbeariaController {
    @Autowired
    private BarbeariaService baS;

    @Autowired
    private BarbeiroService beS;

    @GetMapping("/barbearia/listar")
    public ModelAndView getAutorMV()
    {
        ModelAndView mv = new ModelAndView("BarbeariaTemplate");
        mv.addObject("barbearia", new Barbearia());
        mv.addObject("barbearias", baS.getBarbearia());
        mv.addObject("funcionarios", beS.getBarbeiro());
        return mv;
    }

    @GetMapping("/detalhesBarbearia/{id}")
    public ModelAndView getBarbeariaByIdMV(@PathVariable(name="id") Integer id)
    {
        Barbearia barbAux = baS.getBarbeariaById(id);
        ModelAndView mv = new ModelAndView("BarbeariaDetalhesView");
        mv.addObject("barbearia", baS.getBarbeariaById(id));

        List<Barbeiro> barbeirosNaoAssociados = beS.getBarbeiro();
        barbeirosNaoAssociados.removeAll(barbAux.getFuncionarios());
        mv.addObject("funcionarios", barbeirosNaoAssociados);

        return mv;
    }

    @PostMapping("/associarFuncionario")
    public String saveBarbeariaBarbeiro(@ModelAttribute Barbeiro barbeiro, @RequestParam Integer codB)
    {
        Barbearia barbearia = baS.getBarbeariaById(codB);
        barbeiro = beS.getBarbeiroaById(barbeiro.getIdBarbeiro());

        barbearia.getFuncionarios().add(barbeiro);
        baS.addBarbearia(barbearia);

        return "redirect:/detalhesBarbearia/" + codB;
    }

    @PostMapping("/salvarBarbearia")
    public String saveBarbeariaMV(@ModelAttribute Barbearia barbearia, RedirectAttributes attributes)
    {
        boolean resp = baS.addBarbearia(barbearia);
        if(!resp)
            attributes.addFlashAttribute("erro", "Campo iniciado com SPACE");
        return "redirect:/barbearia/listar";
    }

    @GetMapping("/editarBarbearia")
    public ModelAndView editarBarbearia(@RequestParam Integer idB)
    {
        ModelAndView mv = new ModelAndView("barbeariaEditView");

        Barbearia barbearia = baS.getBarbeariaById(idB);
        mv.addObject("barbearia", barbearia);
        mv.addObject("funcionarios", beS.getBarbeiro());

        return mv;
    }

    @GetMapping("/removerBarbeariaBarbeiro/{idBa}/{idBe}")
    public String removerBarbeiroB(@PathVariable Integer idBa, @PathVariable Integer idBe)
    {
        Barbearia barbearia = baS.getBarbeariaById(idBa);
        Barbeiro barbeiro = beS.getBarbeiroaById(idBe);

        barbearia.getFuncionarios().remove(barbeiro);
        baS.addBarbearia(barbearia);

        return "redirect:/editarBarbearia?idBa=" + idBa;
    }

    @GetMapping("/removerBarbearia")
    public String removerBarbearia(@RequestParam Integer idB, RedirectAttributes attributes)
    {
        Barbearia barbearia = baS.getBarbeariaById(idB);
        boolean resp = baS.removeBarbearia(barbearia);

        if(!resp)
            attributes.addFlashAttribute("erro2", "Barbearia possui funcionarios cadastrados");
        return "redirect:/barbearia/listar";
    }
}