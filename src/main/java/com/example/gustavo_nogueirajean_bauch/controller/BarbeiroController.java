package com.example.gustavo_nogueirajean_bauch.controller;

import java.util.List;

import com.example.gustavo_nogueirajean_bauch.entity.Barbeiro;
import com.example.gustavo_nogueirajean_bauch.entity.Cliente;
import com.example.gustavo_nogueirajean_bauch.service.BarbeariaService;
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
public class BarbeiroController {
    @Autowired
    private BarbeiroService beS;

    @Autowired
    private BarbeariaService baS;

    @Autowired
    private ClienteService cs;

    @GetMapping("/barbeiro/listar")
    public ModelAndView getBarbeiroMV()
    {
        ModelAndView mv = new ModelAndView("BarbeiroTemplate");
        mv.addObject("barbeiro", new Barbeiro());
        mv.addObject("barbeiros", beS.getBarbeiro());
        mv.addObject("barbearias", baS.getBarbearia());
        mv.addObject("clientes", cs.getCliente());
        
        return mv;
    }
    
    @GetMapping("/detalhesBarbeiro/{id}")
    public ModelAndView getBarbeiroByIdMV(@PathVariable(name = "id") Integer id)
    {
        ModelAndView mv = new ModelAndView("BarbeiroDetalhesView");
        Barbeiro barbeiro = beS.getBarbeiroaById(id);
        mv.addObject("barbeiro", barbeiro);

        List<Cliente> clientesNaoAssociados = cs.getCliente();
        clientesNaoAssociados.removeAll(barbeiro.getClientes());
        mv.addObject("clientes", clientesNaoAssociados);
        
        return mv;
    }

    @PostMapping("/associarCliente")
    public String saveBarbeiroCliente(@ModelAttribute Cliente cliente, @RequestParam Integer codB)
    {
        Barbeiro barbeiro = beS.getBarbeiroaById(codB);
        cliente = cs.getClienteById(cliente.getIdCliente());

        barbeiro.getClientes().add(cliente);
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
        mv.addObject("barbearias", baS.getBarbearia());
        mv.addObject("clientes", cs.getCliente());
        
        return mv;
    }

    @GetMapping("/removerBarbeiroCliente/{idB}/{idC}")
    public String removerBarbeiroC(@PathVariable Integer idB, @PathVariable Integer idC)
    {
        Barbeiro barbeiro = beS.getBarbeiroaById(idB);
        Cliente cliente = cs.getClienteById(idC);

        barbeiro.getClientes().remove(cliente);
        beS.addBarbeiro(barbeiro);

        return "redirect:/editarBarbeiro?idB=" + idB;
    }

    @GetMapping("/removerBarbeiro")
    public String removerBarbeiro(@RequestParam Integer idB)
    {
        Barbeiro barbeiro = beS.getBarbeiroaById(idB);
        beS.removeBarbeiro(barbeiro);

        return "redirect:/barbeiro/listar";
    }
}