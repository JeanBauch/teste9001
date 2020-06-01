package com.example.gustavo_nogueirajean_bauch.controller;

import java.util.List;

import com.example.gustavo_nogueirajean_bauch.entity.Barbeiro;
import com.example.gustavo_nogueirajean_bauch.entity.Cliente;
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
public class ClienteController {
    @Autowired
    private ClienteService cs;

    @Autowired 
    private BarbeiroService beS;

    @GetMapping("/cliente/listar")
    public ModelAndView getClienteMV()
    {
        ModelAndView mv = new ModelAndView("ClienteTemplate");
        mv.addObject("cliente", new Cliente());
        mv.addObject("clientes", cs.getCliente());
        mv.addObject("barbeiros", beS.getBarbeiro());
        return mv;
    }

    @GetMapping("/detalhesCliente/{id}")
    public ModelAndView getClienteByIdMV(@PathVariable(name = "id") Integer id)
    {
        Cliente clienteAux = cs.getClienteById(id);
        ModelAndView mv = new ModelAndView("ClienteDetalhesView");
        mv.addObject("cliente", cs.getClienteById(id));

        List<Barbeiro> barbeirosNaoAssociados = beS.getBarbeiro();
        barbeirosNaoAssociados.removeAll(clienteAux.getBarbeiros());
        mv.addObject("barbeiros", barbeirosNaoAssociados);

        return mv;
    }

    @PostMapping("/associarBarbeiro")
    public String saveClienteBarbeiro(@ModelAttribute Barbeiro barbeiro, @RequestParam Integer codC)
    {
        Cliente cliente = cs.getClienteById(codC);
        barbeiro = beS.getBarbeiroaById(barbeiro.getIdBarbeiro());

        cliente.getBarbeiros().add(barbeiro);
        cs.addCliente(cliente);

        return "redirect:/detalhesCliente/" + codC;
    }

    @PostMapping("/salvarCliente")
    public String saveClienteMV(@ModelAttribute Cliente cliente, RedirectAttributes attributes)
    {
        boolean resp = cs.addCliente(cliente);
        if(!resp)
            attributes.addFlashAttribute("erro", "Campo iniciado com SPACE ou valor negativo");
        return "redirect:/cliente/listar";
    }

    @GetMapping("/editarCliente")
    public ModelAndView editarCliente(@RequestParam Integer idC)
    {
        ModelAndView mv = new ModelAndView("ClienteEditView");

        Cliente cliente = cs.getClienteById(idC);
        mv.addObject("cliente", cliente);
        mv.addObject("barbeiros", beS.getBarbeiro());

        return mv;
    }

    @GetMapping("/removerClienteBarbeiro/{idC}/{idB}")
    public String removerBarbeiroC(@PathVariable Integer idC, @PathVariable Integer idB)
    {
        Cliente cliente = cs.getClienteById(idC);
        Barbeiro barbeiro = beS.getBarbeiroaById(idB);

        cliente.getBarbeiros().remove(barbeiro);
        cs.addCliente(cliente);

        return "redirect:/editarCliente?idC=" + idC;
    }

    @GetMapping("/removerCliente")
    public String removerCliente(@RequestParam Integer idC, RedirectAttributes attributes)
    {
        Cliente cliente = cs.getClienteById(idC);
        cs.removeCliente(cliente);
            
        return "redirect:/cliente/listar";
    }

}