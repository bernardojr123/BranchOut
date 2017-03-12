package com.example.bernardojr.branchout.gui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bernardojr.branchout.R;
import com.example.bernardojr.branchout.dominio.Usuario;

/**
 * Created by Bernardojr on 12/03/2017.
 */

public class InformaçõesUsuarioActivity extends AppCompatActivity {


    private TextView primeiroNome;
    private ImageView foto;
    private TextView descricao;
    private TextView nomeCompleto;
    private TextView idade;
    private TextView idiomas;
    private TextView contato;
    private ImageView aceitar;
    private ImageView cancelar;
    private Usuario usuario;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        String s = getIntent().getStringExtra("VALOR");
        if(s == "contato"){
            setContentView(R.layout.activity_informacao_contato);
            initViewsContato();
        }else{
            setContentView(R.layout.activity_informacao_match);
            initViewsMatch();
        }
    }

    private void initViewsContato(){
        primeiroNome = (TextView) findViewById(R.id.informacao_act_contato_primeiro_nome);
        foto = (ImageView) findViewById(R.id.informacao_act_contato_foto);
        descricao = (TextView) findViewById(R.id.informacao_act_contato_descricao);
        nomeCompleto = (TextView) findViewById(R.id.informacao_act_contato_nome);
        idade = (TextView) findViewById(R.id.informacao_act_contato_idade);
        idiomas = (TextView) findViewById(R.id.informacao_act_contato_idiomas);
        contato = (TextView) findViewById(R.id.informacao_act_contato_contato);
    }

    private void initViewsMatch(){
        primeiroNome = (TextView) findViewById(R.id.informacao_act_match_primeiro_nome);
        foto = (ImageView) findViewById(R.id.informacao_act_match_foto);
        descricao = (TextView) findViewById(R.id.informacao_act_match_descricao);
        nomeCompleto = (TextView) findViewById(R.id.informacao_act_match_nome);
        idade = (TextView) findViewById(R.id.informacao_act_match_idade);
        idiomas = (TextView) findViewById(R.id.informacao_act_match_idiomas);
        aceitar = (ImageView) findViewById(R.id.informacao_act_match_btn_aceitar);
        cancelar = (ImageView) findViewById(R.id.informacao_act_match_btn_cancelar);
    }

    private void contato(){

    }

    private void match(){

    }

    private void setCamposContato(){

    }

    private void setCamposMatch(){

    }
}
