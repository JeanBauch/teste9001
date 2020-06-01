package com.example.gustavo_nogueirajean_bauch.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Barbearia implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idBarbearia;
    private String nome;
    private String endereco;
    private String telefone;
    private String cnpj;

    @OneToMany
    @JoinColumn(name="BarbeariaBarbeiro")
    private List<Barbeiro> funcionarios;


    public int getIdBarbearia() {
        return idBarbearia;
    }

    public void setIdBarbearia(int idBarbearia) {
        this.idBarbearia = idBarbearia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    

    public List<Barbeiro> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<Barbeiro> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    
}