package com.example.bernardojr.branchout.dados;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.bernardojr.branchout.dominio.Usuario;
import com.example.bernardojr.branchout.gui.CadastroActivity;
import com.example.bernardojr.branchout.gui.LoginActivity;
import com.example.bernardojr.branchout.gui.MatchFragment;
import com.example.bernardojr.branchout.gui.UsuariosFragment;

import java.util.ArrayList;
import java.util.HashMap;

public class UsuarioDAO {

    private Context context;
    private String urlRequest;

    public UsuarioDAO(Context context) {
        this.context = context;
    }

    public static final String SUCESSO_USUARIO_CADASTRADO = "0";
    public static final String ERRO_USUARIO_JA_CADASTRADO = "1";
    public static final String SUCESSO_LOGIN_E_SENHA_CORRETOS = "2";
    public static final String ERRO_SENHA_INCORRETA = "3";
    public static final String ERRO_USUARIO_INEXISTENTE = "4";
    public static final String SUCESSO_CONSULTA_REALIZADA = "5";
    public static final String ERRO_CONSULTA_NAO_REALIZADA = "6";

    public void validaLogin(String email, String senha) {
        urlRequest = "http://obichoebom.azurewebsites.net/user/userlogin.php?email="+email+"&senha="+senha;
        Log.v("EMAIL E SENHA", email+senha);
        new BackgroundTask().execute("login", urlRequest);
    }

    public void validaCadastro(String email, String senha, String nome, String dataNasc,String descricao, String meiosDecontato, String idiomas, String imagem) {
        //        urlRequest = "http://obichoebom.azurewebsites.net/user/useradd.php?email="+email+"&senha="+
//                senha+"&nome="+nome+"&datanasc="+dataNasc+"&descricao="+descricao+"&meiosdecontato="+
//                meiosDecontato+"&idiomas="+idiomas+"&imagem="+imagem;

        urlRequest = "http://obichoebom.azurewebsites.net/user/useradd.php";
        HashMap<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("senha", senha);
        params.put("nome", nome);
        params.put("datanasc", dataNasc);
        params.put("descricao", descricao);
        params.put("meiosdecontato", meiosDecontato);
        params.put("idiomas", idiomas);
        params.put("imagem", imagem);

        Log.v("URL COMPLETA", urlRequest);
//        new BackgroundTask().execute("cadastro", urlRequest);
        new BackgroundTaskPost(params).execute(urlRequest);
    }

    public void enviaLocalizacao(String id, String x, String y) {
        urlRequest = "http://obichoebom.azurewebsites.net/user/addlocation.php?id="+id+"&x="+x+"&y="+y;
        new BackgroundTask().execute("localizacao", urlRequest);
    }

    public void pegaUsuario(String email, String vControle) {
        urlRequest = "http://obichoebom.azurewebsites.net/user/getusersrelated.php?email="+email;
        Log.v("URL PEGA USUARIOS", urlRequest);
        new BackgroundTaskForRetrievingData(vControle).execute(urlRequest);
    }

    public void pegaUsuarios(String id) {
        urlRequest = "http://obichoebom.azurewebsites.net/user/getusers.php?id="+id;
        new BackgroundTaskForRetrievingAllUsersData().execute(urlRequest);
    }

    public void match(String idUsuario, String idContato, String tipo /*  "A" para aceitar e enviar solicitação e N para negar  */) {
        urlRequest = "http://obichoebom.azurewebsites.net/user/match.php?idUsuario="+idUsuario+"&idContato="+idContato+"&tipo="+tipo;
        new BackgroundTask().execute("match", urlRequest);
    }

    public void atualizaUsuario(String id, String senha, String nome, String dataNasc,String descricao, String meiosDecontato, String idiomas, String imagem) {
        urlRequest = "http://obichoebom.azurewebsites.net/user/userupdate.php?id="+id+"&senha="+
                senha+"&nome="+nome+"&datanasc="+dataNasc+"&descricao="+descricao+"&meiosdecontato="+
                meiosDecontato+"&idiomas="+idiomas+"&imagem="+imagem;

        new BackgroundTask().execute("atualizaU", urlRequest);
    }

    private class BackgroundTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... urls) {
            String[] response = new String[2];
            response[0] = urls[0];
//            response[1] = Funcoes.getStringResponse(urls[1]);
            response[1] = Funcoes.getStringResponse(urls[1], null);
            return response;
        }

        @Override
        protected void onPostExecute(String[] response) {
            Log.v("RESPOSTA DO SERVIDOR", response[1]);
            if(response[0].equals("login"))
                LoginActivity.mostraMensagem(response[1]);
            else if (response[0].equals("cadastro"))
                CadastroActivity.mostraMensagem(response[1]);
            else if (response[0].equals("localizacao"))
                return;
            else if (response[0].equals("match"))
                return;
            else if(response[0].equals("atualizaU"))
                return;  //Caso queira pode chamar um método utilizando a resposta como parâmetro
        }
    }

    private class BackgroundTaskForRetrievingData extends AsyncTask<String, Void, Usuario> {
        private String vControle;

        public BackgroundTaskForRetrievingData(String vControle) {
            this.vControle = vControle;
        }

        @Override
        protected Usuario doInBackground(String... urls) {

            return Funcoes.fetchUserData(urls[0]);
        }

        @Override
        protected void onPostExecute(Usuario usuario) {
            if(vControle.equals("0"))
                LoginActivity.carregaUsuario(usuario);
            else if(vControle.equals("1"))
                MatchFragment.carregaUsuario(usuario);
        }
    }

    private class BackgroundTaskForRetrievingAllUsersData extends AsyncTask<String, Void, ArrayList<Usuario>> {


        @Override
        protected ArrayList<Usuario> doInBackground(String... urls) {
            return Funcoes.fetchAllUsersData(urls[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<Usuario> usuarios) {
            UsuariosFragment.carregarUsuarios(usuarios, context);
        }
    }

    private class BackgroundTaskPost extends AsyncTask<String, Void, String> {
        private HashMap<String, String> params;

        public BackgroundTaskPost(HashMap<String, String> params) {
            this.params = params;
        }

        @Override
        protected String doInBackground(String... urls) {
            return  Funcoes.getStringResponse(urls[0], this.params);
        }

        @Override
        protected void onPostExecute(String response) {
            CadastroActivity.mostraMensagem(response);
        }
    }
}