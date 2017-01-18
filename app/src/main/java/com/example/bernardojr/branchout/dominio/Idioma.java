package com.example.bernardojr.branchout.dominio;

/**
 * Created by romero on 17/01/17.
 */

public class Idioma {
    private String id;
    private String imagem;
    private String nome;
    private int nivel;

    public Idioma(String id, String imagem, String nome, int nivel) {
        this.id = id;
        this.imagem = imagem;
        this.nome = nome;
        this.nivel = nivel;
    }

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

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
}
