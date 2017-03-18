package com.example.bernardojr.branchout.gui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bernardojr.branchout.R;
import com.example.bernardojr.branchout.dominio.Usuario;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


public class UsuariosAdapter extends BaseAdapter {

    private Context context;
    private List<Usuario> usuarios;
    private Boolean contato;
    private static UsuariosAdapter instancia = new UsuariosAdapter();

    public UsuariosAdapter(Context context, List<Usuario> usuarios,Boolean contato) {
        this.context = context;
        this.usuarios = usuarios;
        this.contato = contato;
    }
    public UsuariosAdapter(){}

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Boolean getContato() {
        return contato;
    }

    public void setContato(Boolean contato) {
        this.contato = contato;
    }

    public static UsuariosAdapter getInstancia() {
        return instancia;
    }


    @Override
    public int getCount() {
        return usuarios.size();
    }

    @Override
    public Object getItem(int position) {
        return usuarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return usuarios.get(position).hashCode();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item,null);
        final Usuario usuario = (Usuario) getItem(position);
        ImageView imgUsuario = (ImageView) convertView.findViewById(R.id.usuario_list_img);
        TextView nomeUsuario = (TextView) convertView.findViewById(R.id.usuario_list_txt);

        nomeUsuario.setText(usuario.getNome());
        byte[] decodedString = Base64.decode(usuario.getImagemString(), Base64.URL_SAFE | Base64.NO_WRAP);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imgUsuario.setImageBitmap(decodedByte);


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String data = sdf.format(((Usuario) getItem(position)).getDataNascimento());
                if(contato == true){
                    Intent it = new Intent(context,InformacoesContatoActivity.class);
                    it.putExtra("ID",((Usuario) getItem(position)).getId());
                    it.putExtra("FOTO",((Usuario) getItem(position)).getImagemString());
                    it.putExtra("NOME",((Usuario) getItem(position)).getNome());
                    it.putExtra("DESCRICAO",((Usuario) getItem(position)).getDescricao());
                    it.putExtra("IDIOMAS",((Usuario) getItem(position)).getIdiomas());
                    it.putExtra("CONTATO",((Usuario) getItem(position)).getMeiosDeContato());
                    it.putExtra("DATA",data);
                    context.startActivity(it);



                }
                else{
                    Intent it = new Intent(context,InformacoesMatchActivity.class);
                    it.putExtra("ID",((Usuario) getItem(position)).getId());
                    it.putExtra("FOTO",((Usuario) getItem(position)).getImagemString());
                    it.putExtra("NOME",((Usuario) getItem(position)).getNome());
                    it.putExtra("DESCRICAO",((Usuario) getItem(position)).getDescricao());
                    it.putExtra("IDIOMAS",((Usuario) getItem(position)).getIdiomas());
                    it.putExtra("DATA",data);
                    context.startActivity(it);
                }
            }
        });

        return convertView;
    }

}
