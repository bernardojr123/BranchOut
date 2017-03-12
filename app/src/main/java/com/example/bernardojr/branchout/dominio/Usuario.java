package com.example.bernardojr.branchout.dominio;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by romero on 17/01/17.
 */

public class Usuario {
    private String id;
    private Bitmap imagem;
    private String imagemString;
    private String nome;
    private String senha;
    private String email;
    private Date dataNascimento;
    private String dataNString;
    private String descricao;
    private String meiosDeContato;
    private String idiomas;
    private ArrayList<Usuario> solicitacoes;
    private ArrayList<Usuario> contatos;

    public Usuario(String id, String imagemString, String nome, String senha, String email, String dataNString, String descricao, String meiosDeContato, String idiomas, ArrayList<Usuario> solicitacoes, ArrayList<Usuario> contatos) {
        this.id = id;
        this.imagemString = imagemString;
        this.nome = nome;
        this.senha = senha;
        this.email = email;
        this.dataNString = dataNString;
        this.descricao = descricao;
        this.meiosDeContato = meiosDeContato;
        this.idiomas = idiomas;
        this.solicitacoes = solicitacoes;
        this.contatos = contatos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Bitmap getImagem() {
        return imagem;
    }

    public void setImagem(Bitmap imagem) {
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

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
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
