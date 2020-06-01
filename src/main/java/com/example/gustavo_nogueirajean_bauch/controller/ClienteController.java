package com.example.gustavo_nogueirajean_bauch.controller;

import com.example.gustavo_nogueirajean_bauch.entity.Agendamento;
import com.example.gustavo_nogueirajean_bauch.entity.Cliente;
import com.example.gustavo_nogueirajean_bauch.service.AgendamentoService;
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
    private AgendamentoService as;

    @GetMapping("/cliente/listar")
    public ModelAndView getClienteMV()
    {
        ModelAndView mv = new ModelAndView("ClienteTemplate");
        mv.addObject("cliente", new Cliente());
        mv.addObject("clientes", cs.getCliente());
        
        mv.addObject("agendamentos", as.getAgendamento());
        return mv;
    }

    @GetMapping("/detalhesCliente/{id}")
    public ModelAndView getClienteByIdMV(@PathVariable(name = "id") Integer id)
    {
        ModelAndView mv = new ModelAndView("ClienteDetalhesView");
        mv.addObject("cliente", cs.getClienteById(id));
        return mv;
    }

    @GetMapping("/cadastrarAgendamentoPorCliente/barbearia/{id}")
    public ModelAndView cadastroAgendamentoPorClienteMV(@PathVariable(name = "id") Integer id)
    {
        ModelAndView mv = new ModelAndView("ClienteAgendamentoTemplate");
        Cliente clienteAux = cs.getClienteById(id);
        mv.addObject("cliente", clienteAux);
        mv.addObject("agendamentos", as.getAgendamento());
        return mv;
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
        mv.addObject("agendamentos", as.getAgendamento());

        return mv;
    }

    @GetMapping("/removerClienteAgendamento/{idC}/{idA}")
    public String removerAgendamentoC(@PathVariable Integer idC, @PathVariable Integer idA)
    {
        Cliente cliente = cs.getClienteById(idC);
        Agendamento agendamento = as.getAgendamentoById(idA);

        cliente.getAgendamento().remove(agendamento);
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