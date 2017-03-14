package com.example.bernardojr.branchout.dados;

import com.example.bernardojr.branchout.dominio.Usuario;

import java.util.ArrayList;

/**
 * Created by romero on 12/03/17.
 */

public class Sessao {

    private static Sessao instancia = new Sessao();
    private static Usuario usuario;
    private static ArrayList<Usuario> usuariosProximos = new ArrayList<>();

    public static ArrayList<Usuario> getUsuariosProximos() {
        return usuariosProximos;
    }

    public static void setUsuariosProximos(ArrayList<Usuario> usuariosProximos) {
        Sessao.usuariosProximos = usuariosProximos;
    }

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
