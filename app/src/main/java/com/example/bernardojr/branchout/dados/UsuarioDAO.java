package com.example.bernardojr.branchout.dados;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.bernardojr.branchout.dominio.Usuario;
import com.example.bernardojr.branchout.gui.CadastroActivity;
import com.example.bernardojr.branchout.gui.LoginActivity;

import java.util.ArrayList;

public class UsuarioDAO {

    private Context mContext;
    private String urlRequest;

    public static final String SUCESSO_USUARIO_CADASTRADO = "0";
    public static final String ERRO_USUARIO_JA_CADASTRADO = "1";
    public static final String SUCESSO_LOGIN_E_SENHA_CORRETOS = "2";
    public static final String ERRO_SENHA_INCORRETA = "3";
    public static final String ERRO_USUARIO_INEXISTENTE = "4";

    public UsuarioDAO(Context mContext) {
        this.mContext = mContext;
    }

    public void validaLogin(String email, String senha)
    {
        urlRequest = "http://obichoebom.azurewebsites.net/user/userlogin.php?email="+email+"&senha="+senha;
        Log.v("EMAIL E SENHA", email+senha);
        new BackgroundTaskForValidation().execute("login", urlRequest);
    }

    public void validaCadastro(String email, String senha, String nome, String dataNasc,String descricao, String meiosDecontato, String idiomas, String imagem)
    {
        urlRequest = "http://obichoebom.azurewebsites.net/user/useradd.php?email="+email+"&senha="+
                senha+"&nome="+nome+"&datanasc="+dataNasc+"&descricao="+descricao+"&meiosdecontato="+
                meiosDecontato+"&idiomas="+idiomas+"&imagem="+imagem;

        Log.v("URL COMPLETA", urlRequest);
        new BackgroundTaskForValidation().execute("cadastro", urlRequest);
    }

    public void pegaUsuario(String email)
    {
        urlRequest = "http://obichoebom.azurewebsites.net/user/getusersrelated.php?email="+email;
        Log.v("URL PEGA USUARIOS", urlRequest);
        new BackgroundTaskForRetrievingData().execute(urlRequest);
    }

    private class BackgroundTaskForValidation extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... urls) {
            String[] response = new String[2];
            response[0] = urls[0];
            response[1] = Funcoes.getStringResponse(urls[1]);

            return response;
        }

        @Override
        protected void onPostExecute(String[] response) {
            Log.v("RESPOSTA DO SERVIDOR", response[1]);
            if(response[0].equals("login"))
                LoginActivity.mostraMensagem(response[1]);
            else if (response[0].equals("cadastro"))
                CadastroActivity.mostraMensagem(response[1]);
        }
    }

    private class BackgroundTaskForRetrievingData extends AsyncTask<String, Void, Usuario> {

        @Override
        protected Usuario doInBackground(String... urls) {

            return Funcoes.fetchUserData(urls[0]);
        }

        @Override
        protected void onPostExecute(Usuario usuario) {
            LoginActivity.carregaUsuario(usuario);
        }
    }
}