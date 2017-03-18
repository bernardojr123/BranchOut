package com.example.bernardojr.branchout.gui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bernardojr.branchout.R;
import com.example.bernardojr.branchout.dados.Sessao;
import com.example.bernardojr.branchout.dados.UsuarioDAO;
import com.example.bernardojr.branchout.dominio.Usuario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.DATE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class InformacoesMatchActivity extends AppCompatActivity {

    private TextView txtPrimeiroNome;
    private ImageView imgFoto;
    private TextView txtDescricao;
    private TextView txtNomeCompleto;
    private TextView txtIdade;
    private TextView txtIdiomas;
    private Usuario usuario;
    private ImageView btnAceitar;
    private ImageView btnRecusar;

    private Bitmap foto;
    private String descricao;
    private String nomeCompleto;
    private Date dataNascimento;
    private String idiomas;
    private String id;
    private String fotoStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacao_match);
        initViews();
        if (Sessao.getInstancia().getUsuario() != null){
            usuario = Sessao.getInstancia().getUsuario();
            setCampos();
        }

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * 1), (int) (height * .80));

        btnAceitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsuarioDAO usuarioDAO = new UsuarioDAO(getBaseContext());
                usuarioDAO.match(Sessao.getInstancia().getUsuario().getId(),id,"A");
                finish();
            }
        });

        btnRecusar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsuarioDAO usuarioDAO = new UsuarioDAO(getBaseContext());
                usuarioDAO.match(Sessao.getInstancia().getUsuario().getId(),id,"N");
                finish();
            }
        });
    }

    private void initViews(){
        txtPrimeiroNome = (TextView) findViewById(R.id.informacao_act_match_primeiro_nome);
        imgFoto = (ImageView) findViewById(R.id.informacao_act_match_foto);
        txtDescricao = (TextView) findViewById(R.id.informacao_act_match_descricao);
        txtNomeCompleto = (TextView) findViewById(R.id.informacao_act_match_nome);
        txtIdade = (TextView) findViewById(R.id.informacao_act_match_idade);
        txtIdiomas = (TextView) findViewById(R.id.informacao_act_match_idiomas);
        btnAceitar = (ImageView) findViewById(R.id.informacao_act_match_btn_aceitar);
        btnRecusar = (ImageView) findViewById(R.id.informacao_act_match_btn_cancelar);
    }

    private void setCampos(){
        pegarExtras();
        String pNome;
        if(nomeCompleto.contains(" ")) {
            String[] splitArray = nomeCompleto.split("\\s+");
            pNome = splitArray[0];
        }else{
            pNome = nomeCompleto;
        }
        byte[] decodedString = Base64.decode(fotoStr, Base64.URL_SAFE | Base64.NO_WRAP);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imgFoto.setImageBitmap(decodedByte);
        txtPrimeiroNome.setText(pNome);
        //foto = (ImageView) findViewById(R.id.informacao_act_contato_foto);
        txtDescricao.setText(descricao);
        txtNomeCompleto.setText(nomeCompleto);
        Date hj = new Date();
        int diffInYear = getDiffYears(dataNascimento,hj);
        txtIdade.setText(diffInYear + "");
        txtIdiomas.setText(idiomas);
    }

    private int getDiffYears(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(YEAR) - a.get(YEAR);
        if (a.get(MONTH) > b.get(MONTH) ||
                (a.get(MONTH) == b.get(MONTH) && a.get(DATE) > b.get(DATE))) {
            diff--;
        }
        return diff;
    }

    private Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    private void pegarExtras(){
        id = getIntent().getStringExtra("ID");
        fotoStr = getIntent().getStringExtra("FOTO");
        nomeCompleto = getIntent().getStringExtra("NOME");
        descricao = getIntent().getStringExtra("DESCRICAO");
        idiomas = getIntent().getStringExtra("IDIOMAS");
        String dat = getIntent().getStringExtra("DATA");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dataNascimento = formatter.parse(dat);
        } catch (ParseException e) {

        }

    }
}
