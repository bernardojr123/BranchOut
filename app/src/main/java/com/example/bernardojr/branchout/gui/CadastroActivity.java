package com.example.bernardojr.branchout.gui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.bernardojr.branchout.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CadastroActivity extends AppCompatActivity {

    private EditText edtNome;
    private EditText edtEmail;
    private EditText edtSenha;
    private EditText edtRepetirSenha;
    private EditText edtDataNascimento;
    private EditText edtMeiosContato;
    private EditText edtDescricao;
    private Button btnCadastrar;

    private String nome;
    private String email;
    private String senha;
    private String repetirSenha;
    private Date dataNascimento;
    private String meiosContato;
    private String descricao;

    private int ano;
    private int mes;
    private int dia;

    private Resources resources;
    private SimpleDateFormat formatter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        initViews();
        final Calendar calendar = Calendar.getInstance();
        ano = calendar.get(Calendar.YEAR);
        mes = calendar.get(Calendar.MONTH);
        dia = calendar.get(Calendar.DAY_OF_MONTH);

        edtDataNascimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escolherDataNascimento();
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarCampos();
            }
        });
    }

    private void initViews() {
        edtNome = (EditText) findViewById(R.id.cadastro_activity_nome);
        edtEmail = (EditText) findViewById(R.id.cadastro_activity_email);
        edtSenha = (EditText) findViewById(R.id.cadastro_activity_senha);
        edtRepetirSenha = (EditText) findViewById(R.id.cadastro_activity_repetir_senha);
        edtDataNascimento = (EditText) findViewById(R.id.cadastro_activity_data_nascimento);
        edtMeiosContato = (EditText) findViewById(R.id.cadastro_activity_meios_contato);
        edtDescricao = (EditText) findViewById(R.id.cadastro_activity_descricao);
        btnCadastrar = (Button) findViewById(R.id.cadastro_activity_btn_cadastrar);
        edtDataNascimento.setFocusable(false);
    }

    private boolean validaCamposVazios(String nome, String email, String senha, String reSenha,
                                       String dataNasc, String meioContato, String descricao) {
        if (TextUtils.isEmpty(nome)) {
            edtNome.requestFocus();
            edtNome.setError(resources.getString(R.string.cadastro_activity_nome_vazio));
            return true;
        } else if (TextUtils.isEmpty(email)){
            edtEmail.requestFocus();
            edtEmail.setError(resources.getString(R.string.cadastro_activity_email_vazio));
            return true;
        } else if (TextUtils.isEmpty(senha)) {
            edtSenha.requestFocus();
            edtSenha.setError(resources.getString(R.string.cadastro_activity_senha_vazia));
            return true;
        } else if (TextUtils.isEmpty(reSenha)) {
            edtRepetirSenha.requestFocus();
            edtRepetirSenha.setError(resources.getString(R.string.cadastro_activity_repetir_senha_vazia));
            return true;
        } else if (TextUtils.isEmpty(dataNasc)) {
            edtDataNascimento.requestFocus();
            edtDataNascimento.setError(resources.getString(R.string.cadastro_activity_data_nascimento_vazia));
            return true;
        } else if (TextUtils.isEmpty(meioContato)) {
            edtMeiosContato.requestFocus();
            edtMeiosContato.setError(resources.getString(R.string.cadastro_activity_meios_de_contato_vazio));
            return true;
        } else if (TextUtils.isEmpty(descricao)) {
            edtDescricao.requestFocus();
            edtDescricao.setError(resources.getString(R.string.cadastro_activity_descricao_vazia));
            return true;
        }
        return false;
    }

    public void escolherDataNascimento(){
        final DatePickerDialog datepicker = new DatePickerDialog(CadastroActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int mesCerto = monthOfYear + 1;
                edtDataNascimento.setText(dayOfMonth + "/" + mesCerto + "/" + year);

            }
        }, ano, mes, dia);
        datepicker.show();
    }

    private boolean validarCampos(){
        formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dataNascimento = formatter.parse(edtDataNascimento.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        nome = edtNome.getText().toString();
        email = edtEmail.getText().toString().trim();
        senha = edtSenha.getText().toString();
        repetirSenha = edtRepetirSenha.getText().toString();
        meiosContato = edtMeiosContato.getText().toString();
        descricao = edtDescricao.getText().toString();

        return (isValidEmail(email) && hasSizeValidCadastro(nome,senha,repetirSenha,
                meiosContato,descricao) && hasNoSpaceCadastro(email,senha));


    }


    private boolean isValidEmail(CharSequence email) {
        if (!(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())){
            edtEmail.requestFocus();
            edtEmail.setError(resources.getString(R.string.cadastro_activity_email_invalido));
            return false;
        }
        return true;
    }

    private boolean hasSizeValidCadastro(String nome, String senha, String reSenha,
                                         String meioContato, String descricao) {

        if (!(senha.equals(reSenha))){
            edtRepetirSenha.requestFocus();
            edtRepetirSenha.setError(resources.getString(R.string.cadastro_activity_senhas_nao_correspondem));
            return false;
        }
        if (!(nome.length() > 4)) {
            edtNome.requestFocus();
            edtNome.setError(resources.getString(R.string.cadastro_activity_nome_curto));
            return true;
        } else if (!(meioContato.length() > 10)) {
            edtMeiosContato.requestFocus();
            edtMeiosContato.setError(resources.getString(R.string.cadastro_activity_meio_contato_curto));
            return true;
        } else if (!(descricao.length() > 4)) {
            edtDescricao.requestFocus();
            edtDescricao.setError(resources.getString(R.string.cadastro_activity_descricao_curta));
            return true;
        }
        return true;
    }

    private boolean hasNoSpaceCadastro(String email, String senha) {

        if (email.indexOf(" ") != -1){
            edtEmail.requestFocus();
            edtEmail.setError(resources.getString(R.string.cadastro_activity_email_invalido));
            return true;
        }else if (senha.indexOf(" ") != -1){
            edtSenha.requestFocus();
            edtSenha.setError(resources.getString(R.string.cadastro_activity_senha_invalida));
            return true;
        }return false;
    }

}
