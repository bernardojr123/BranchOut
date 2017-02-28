package com.example.bernardojr.branchout.gui;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bernardojr.branchout.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static com.example.bernardojr.branchout.gui.CadastroActivity.REQUEST_IMAGE_CAPTURE;

/**
 * Created by romero on 19/01/17.
 */

public class PerfilFragment extends Fragment {

    private ImageView imgFoto;
    private EditText edtNome;
    private EditText edtEmail;
    private EditText edtData;
    private EditText edtMeioContato;
    private EditText edtDescricao;
    private Button btnProx;
    private Button btnVoltar;

    private String nome;
    private String dataN;
    private Date dataNascimento;
    private String meiosContato;
    private String descricao;

    private int ano;
    private int mes;
    private int dia;

    private SimpleDateFormat formatter;

    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_perfil_alterar, container, false);
        initView(rootView);
        telaDefault();
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public void escolherDataNascimento(final EditText editText) {
        final DatePickerDialog datepicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int mesCerto = monthOfYear + 1;
                editText.setText(dayOfMonth + "/" + mesCerto + "/" + year);

            }
        }, ano, mes, dia);
        datepicker.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgFoto.setImageBitmap(imageBitmap);
        }
    }

    private void liberarEdicaoCampos(){
        imgFoto.setEnabled(true);
        edtNome.setEnabled(true);
        edtData.setEnabled(true);
        edtMeioContato.setEnabled(true);
        edtDescricao.setEnabled(true);
    }

    private void travarEdicaoCampos(){
        imgFoto.setEnabled(false);
        edtNome.setEnabled(false);
        edtEmail.setEnabled(false);
        edtData.setEnabled(false);
        edtMeioContato.setEnabled(false);
        edtDescricao.setEnabled(false);
    }

    private void telaEditar(){
        liberarEdicaoCampos();
        imgFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        edtData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escolherDataNascimento();
            }
        });
        btnVoltar.setText(R.string.fragment_perfil_btn_cancelar);
        btnVoltar.setVisibility(View.VISIBLE);
        btnProx.setText(R.string.fragment_perfil_btn_finalizar);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telaDefault();
            }
        });
        btnProx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarCampos() == true){
                    Toast.makeText(context,"foi",Toast.LENGTH_SHORT).show();
                    telaDefault();
                }
            }
        });
    }

    private void telaDefault(){
        travarEdicaoCampos();
        btnVoltar.setVisibility(View.INVISIBLE);
        btnProx.setText(R.string.fragment_perfil_btn_alterar);
        btnProx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telaEditar();
                Toast.makeText(context,"foir",Toast.LENGTH_SHORT);
            }
        });
    }

    private void initView(View view) {
        final Calendar calendar = Calendar.getInstance();
        ano = calendar.get(Calendar.YEAR);
        mes = calendar.get(Calendar.MONTH);
        dia = calendar.get(Calendar.DAY_OF_MONTH);
        imgFoto = (ImageView) view.findViewById(R.id.perfil_fragment_img_foto);
        edtNome = (EditText) view.findViewById(R.id.perfil_fragment_nome);
        edtEmail = (EditText) view.findViewById(R.id.perfil_fragment_email);
        edtData = (EditText) view.findViewById(R.id.perfil_fragment_data_nascimento);
        edtMeioContato = (EditText) view.findViewById(R.id.perfil_fragment_meios_contato);
        edtDescricao = (EditText) view.findViewById(R.id.perfil_fragment_descricao);
        btnVoltar = (Button) view.findViewById(R.id.perfil_fragment_btn_voltar);
        btnProx = (Button) view.findViewById(R.id.perfil_fragment_btn_prox);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
    public void escolherDataNascimento(){
        final DatePickerDialog datepicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                int mesCerto = monthOfYear + 1;
                edtData.setText(dayOfMonth + "/" + mesCerto + "/" + year);

            }
        }, ano, mes, dia);
        datepicker.show();
    }
    private boolean temTamanhoValido(String nome, String meioContato, String descricao) {

        if (!(nome.length() > 4)) {
            edtNome.requestFocus();
            edtNome.setError(getResources().getString(R.string.erro_nome_curto));
            return false;
        } else if (!(meioContato.length() > 10)) {
            edtMeioContato.requestFocus();
            edtMeioContato.setError(getResources().getString(R.string.erro_meio_contato_curto));
            return false;
        } else if (!(descricao.length() > 4)) {
            edtDescricao.requestFocus();
            edtDescricao.setError(getResources().getString(R.string.erro_descricao_curta));
            return false;
        }
        return true;
    }
    private boolean validaCamposVazios(String nome, String dataNasc, String meioContato, String descricao) {
        if (TextUtils.isEmpty(nome)) {
            edtNome.requestFocus();
            edtNome.setError(getResources().getString(R.string.erro_nome_vazio));
            return false;
        }else if (TextUtils.isEmpty(dataNasc)) {
            edtData.requestFocus();
            edtData.setError(getResources().getString(R.string.erro_data_nascimento_vazia));
            return false;
        } else if (TextUtils.isEmpty(meioContato)) {
            edtMeioContato.requestFocus();
            edtMeioContato.setError(getResources().getString(R.string.erro_meios_de_contato_vazio));
            return false;
        } else if (TextUtils.isEmpty(descricao)) {
            edtDescricao.requestFocus();
            edtDescricao.setError(getResources().getString(R.string.erro_descricao_vazia));
            return false;
        }
        return true;
    }
    private boolean validarCampos(){
        formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dataNascimento = formatter.parse(edtData.getText().toString());
        } catch (ParseException e) {
            //tratar esse erro
            e.printStackTrace();
        }
        dataN = edtData.getText().toString();
        nome = edtNome.getText().toString();
        meiosContato = edtMeioContato.getText().toString();
        descricao = edtDescricao.getText().toString();

        return (validaCamposVazios(nome,dataN,meiosContato,descricao) &&
                temTamanhoValido(nome,meiosContato,descricao));
    }
}
