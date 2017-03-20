package com.example.bernardojr.branchout.gui;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bernardojr.branchout.R;
import com.example.bernardojr.branchout.dados.Sessao;
import com.example.bernardojr.branchout.dados.UsuarioDAO;
import com.example.bernardojr.branchout.dominio.Usuario;

import java.io.ByteArrayOutputStream;
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
    private EditText edtIdiomas;
    private Button btnProx;
    private Button btnVoltar;
    private EditText edtSenha;
    private EditText edtRepetirSenha;


    private String nome;
    private String dataN;
    private Date dataNascimento;
    private String meiosContato;
    private String descricao;
    private String idiomas;

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

        if (Sessao.getInstancia().getUsuario() != null) {
            preencherCampos(Sessao.getInstancia().getUsuario());
        }
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
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
        edtIdiomas.setEnabled(true);
        edtSenha.setEnabled(true);
        edtRepetirSenha.setEnabled(true);
    }

    private void travarEdicaoCampos(){
        imgFoto.setEnabled(false);
        edtNome.setEnabled(false);
        edtEmail.setEnabled(false);
        edtData.setEnabled(false);
        edtMeioContato.setEnabled(false);
        edtDescricao.setEnabled(false);
        edtIdiomas.setEnabled(false);
        edtSenha.setEnabled(false);
        edtRepetirSenha.setEnabled(false);
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
                UsuarioDAO usuarioDAO = new UsuarioDAO(getActivity());
                usuarioDAO.pegaUsuario(Sessao.getInstancia().getUsuario().getEmail(),"1");
                preencherCampos(Sessao.getInstancia().getUsuario());
            }
        });
        btnProx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validarCampos() == true){
                    UsuarioDAO usuarioDAO = new UsuarioDAO(getActivity());
                    String id = Sessao.getInstancia().getUsuario().getId();
                    String nome = edtNome.getText().toString();
                    String data = edtData.getText().toString();
                    String descricao = edtDescricao.getText().toString();
                    String contato = edtMeioContato.getText().toString();
                    String idioma = edtIdiomas.getText().toString();
                    String foto = imgToBase64();
                    String senha = edtSenha.getText().toString();
                    usuarioDAO.atualizaUsuario(id,senha,nome.replace(" ","%20"),data,descricao.replace(" ","%20"),
                            contato.replace(" ","%20"),idioma.replace(" ","%20"),foto);
                    telaDefault();
                    usuarioDAO.pegaUsuario(Sessao.getInstancia().getUsuario().getEmail(),"1");
                    preencherCampos(Sessao.getInstancia().getUsuario());
                    Toast.makeText(getActivity(),"Atualizado com sucesso!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void limpaCampos(){
        edtNome.setText(null);
        edtEmail.setText(null);
        imgFoto.setImageBitmap(null);
        edtData.setText(null);
        edtMeioContato.setText(null);
        edtDescricao.setText(null);
        edtIdiomas.setText(null);
        edtSenha.setText(null);
        edtRepetirSenha.setText(null);
    }

    private void preencherCampos(Usuario usuario){
        edtNome.setText(usuario.getNome().replace("%20"," "));
        edtEmail.setText(usuario.getEmail());
        byte[] decodedString = Base64.decode(usuario.getImagemString(), Base64.URL_SAFE | Base64.NO_WRAP);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imgFoto.setImageBitmap(decodedByte);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String data = sdf.format(usuario.getDataNascimento());
        edtData.setText(data);
        edtMeioContato.setText(usuario.getMeiosDeContato().replace("%20"," "));
        edtDescricao.setText(usuario.getDescricao().replace("%20"," "));
        edtIdiomas.setText(usuario.getIdiomas().replace("%20"," "));
    }

    private void telaDefault(){
        travarEdicaoCampos();
        edtSenha.setText(null);
        edtRepetirSenha.setText(null);
        btnVoltar.setVisibility(View.INVISIBLE);
        btnProx.setText(R.string.fragment_perfil_btn_alterar);
        btnProx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telaEditar();
            }
        });
    }

    private void initView(View view) {
        final Calendar calendar = Calendar.getInstance();
        ano = calendar.get(Calendar.YEAR);
        mes = calendar.get(Calendar.MONTH);
        dia = calendar.get(Calendar.DAY_OF_MONTH);
        edtSenha = (EditText) view.findViewById(R.id.perfil_fragment_senha);
        edtRepetirSenha = (EditText) view.findViewById(R.id.perfil_fragment_repetir_senha);
        imgFoto = (ImageView) view.findViewById(R.id.perfil_fragment_img_foto);
        edtNome = (EditText) view.findViewById(R.id.perfil_fragment_nome);
        edtEmail = (EditText) view.findViewById(R.id.perfil_fragment_email);
        edtData = (EditText) view.findViewById(R.id.perfil_fragment_data_nascimento);
        edtMeioContato = (EditText) view.findViewById(R.id.perfil_fragment_meios_contato);
        edtDescricao = (EditText) view.findViewById(R.id.perfil_fragment_descricao);
        edtIdiomas = (EditText) view.findViewById(R.id.perfil_fragment_idiomas);
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

    private boolean temTamanhoValido(String nome, String meioContato, String descricao,String idioma, String senha,String reSenha) {

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
        }else if (!(idioma.length()>10)){
            edtIdiomas.requestFocus();
            edtIdiomas.setError(getResources().getString(R.string.erro_idiomas_curto));
        }else if (!(senha.equals(reSenha)) && !(senha.length()>4)){
            edtRepetirSenha.requestFocus();
            edtRepetirSenha.setError(getResources().getString(R.string.erro_senhas_nao_correspondem));
            return false;
        }
        return true;
    }
    private boolean validaCamposVazios(String nome, String dataNasc, String meioContato, String descricao, String idiomas, String senha) {
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
        }else if(TextUtils.isEmpty(idiomas)) {
            edtIdiomas.requestFocus();
            edtIdiomas.setError(getResources().getString(R.string.erro_idiomas_vazio));
            return false;
        }else if (TextUtils.isEmpty(senha)) {
            edtSenha.requestFocus();
            edtSenha.setError(getResources().getString(R.string.erro_senha_vazia));
            return false;}

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
        idiomas = edtIdiomas.getText().toString();
        String senha = edtSenha.getText().toString();
        String reSenha = edtRepetirSenha.getText().toString();

        return (validaCamposVazios(nome,dataN,meiosContato,descricao,idiomas,senha) &&
                temTamanhoValido(nome,meiosContato,descricao,idiomas,senha,reSenha) && naoTemEspaco(senha));
    }

    private boolean naoTemEspaco(String senha) {

        if (senha.contains(" ")){
            edtSenha.requestFocus();
            edtSenha.setError(getResources().getString(R.string.erro_senha_invalida));
            return false;
        }return true;
    }

    private String imgToBase64(){
        Bitmap bitmap = ((BitmapDrawable)imgFoto.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();

        String foto = Base64.encodeToString(b,Base64.URL_SAFE | Base64.NO_WRAP);
        Log.e("FOTO", foto);
        return foto;
    }
}
