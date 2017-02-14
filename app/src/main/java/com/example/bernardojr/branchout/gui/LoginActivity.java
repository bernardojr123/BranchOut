package com.example.bernardojr.branchout.gui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bernardojr.branchout.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class LoginActivity extends AppCompatActivity {
    private EditText edtEmail;
    private EditText edtSenha;
    private TextView esqSenha;
    private TextView cadastrar;
    private Button btnLogin;
    private String email;
    private String senha;

    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        resources = this.getResources();
        initView();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarCampos()){
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                }
            }
        });
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


    private void initView(){

        edtEmail = (EditText) findViewById(R.id.Login_activity_email);
        edtSenha = (EditText) findViewById(R.id.Login_activity_senha);
        btnLogin = (Button) findViewById(R.id.Login_activity_btn_login);
        esqSenha = (TextView) findViewById(R.id.Login_activity_esqueceu);
        cadastrar = (TextView)findViewById(R.id.Login_activity_cadastrar);

    }


    private boolean validaVazios(String email, String senha) {
        if (TextUtils.isEmpty(email)) {
            edtEmail.requestFocus();
            edtEmail.setError(resources.getString(R.string.Login_activity_email_vazio));
            return false;
        } else if (TextUtils.isEmpty(senha)) {
            edtSenha.requestFocus();
            edtSenha.setError(resources.getString(R.string.Login_activity_senha_vazia));
            return false;
        }
        return true;
    }

    private boolean validarCampos(){
        email = edtEmail.getText().toString().trim();
        senha = edtSenha.getText().toString();

        return (validaVazios(email,senha) &&
                validarEmail(email) && validarSenha(senha));


    }

    private boolean validarEmail(CharSequence email) {
        if (!(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())){
            edtEmail.requestFocus();
            edtEmail.setError(resources.getString(R.string.Login_activity_email_invalido));
            return false;
        }
        return true;
    }

    private boolean validarSenha(String senha){

        if (senha.length() < 4) {
            edtSenha.requestFocus();
            edtSenha.setError(resources.getString(R.string.Login_activity_senha_curta));
            return false;
        }
        return true;
    }
}
