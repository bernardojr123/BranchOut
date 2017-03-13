package com.example.bernardojr.branchout.dados;

import com.example.bernardojr.branchout.dominio.Usuario;

/**
 * Created by romero on 12/03/17.
 */

public class Sessao {

    private static Sessao instancia = new Sessao();
    private static Usuario usuario;

    private Sessao(){

    }

    public static Sessao getInstancia(){
        return instancia;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
