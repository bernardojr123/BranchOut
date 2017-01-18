package com.example.bernardojr.branchout.dominio;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by romero on 17/01/17.
 */

public class Usuario {
    private String id;
    private String imagem;
    private String nome;
    private String senha;
    private String email;
    private Date dataNascimento;
    private String descricao;
    private String meiosDeContato;
    private ArrayList<Idioma> idiomas;
    private ArrayList<Usuario> solicitacoes;
    private ArrayList<Usuario> contatos;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMeiosDeContato() {
        return meiosDeContato;
    }

    public void setMeiosDeContato(String meiosDeContato) {
        this.meiosDeContato = meiosDeContato;
    }

    public ArrayList<Idioma> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(ArrayList<Idioma> idiomas) {
        this.idiomas = idiomas;
    }

    public ArrayList<Usuario> getSolicitacoes() {
        return solicitacoes;
    }

    public void setSolicitacoes(ArrayList<Usuario> solicitacoes) {
        this.solicitacoes = solicitacoes;
    }

    public ArrayList<Usuario> getContatos() {
        return contatos;
    }

    public void setContatos(ArrayList<Usuario> contatos) {
        this.contatos = contatos;
    }
}
