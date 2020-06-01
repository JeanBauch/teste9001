package com.example.gustavo_nogueirajean_bauch.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;


@Entity
public class Especializacao implements Serializable{
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idEspecializacao;
    private String nome;
    private String descricao;

    @ManyToMany
    @JoinTable(
        name="BarbeiroEspecializacao",
        joinColumns = @JoinColumn(
            name="idEspecializacao",
            referencedColumnName = "idEspecializacao"
        ),
        inverseJoinColumns = @JoinColumn(
            name="idBarbeiro",
            referencedColumnName = "idBarbeiro"
        )
    )
    List<Barbeiro> barbeiros;

    public int getIdEspecializacao() {
        return idEspecializacao;
    }

    public void setIdEspecializacao(int idEspecializacao) {
        this.idEspecializacao = idEspecializacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Barbeiro> getBarbeiros() {
        return barbeiros;
    }

    public void setBarbeiros(List<Barbeiro> barbeiros) {
        this.barbeiros = barbeiros;
    }
}