package com.example.gustavo_nogueirajean_bauch.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;



@Entity
public class Barbeiro implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idBarbeiro;
    private String nome;
    private String telefone;
    private int idade;
    private double salario;

    @OneToMany
    @JoinColumn(name="AgendamentoBarbeiro")
    private List<Agendamento> agendamento;

    @ManyToMany
    @JoinTable(
        name="BarbeiroEspecializacao",
        joinColumns = @JoinColumn(
            name="idBarbeiro",
            referencedColumnName = "idBarbeiro"
        ),
        inverseJoinColumns = @JoinColumn(
            name="idEspecializacao",
            referencedColumnName = "idEspecializacao"
        )
    )
    private List<Especializacao> especializacao;

    public int getIdBarbeiro() {
        return idBarbeiro;
    }

    public void setIdBarbeiro(int idBarbeiro) {
        this.idBarbeiro = idBarbeiro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public List<Agendamento> getAgendamento() {
        return agendamento;
    }

    public void setAgendamento(List<Agendamento> agendamento) {
        this.agendamento = agendamento;
    }

    public List<Especializacao> getEspecializacao() {
        return especializacao;
    }

    public void setEspecializacao(List<Especializacao> especializacao) {
        this.especializacao = especializacao;
    }

    

    

}