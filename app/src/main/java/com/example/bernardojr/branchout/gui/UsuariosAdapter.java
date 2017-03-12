package com.example.bernardojr.branchout.gui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bernardojr.branchout.R;
import com.example.bernardojr.branchout.dominio.Usuario;

import java.util.List;


public class UsuariosAdapter extends BaseAdapter {

    private Context context;
    private List<Usuario> usuarios;
    private Boolean contato;

    public UsuariosAdapter(Context context, List<Usuario> usuarios,Boolean contato) {
        this.context = context;
        this.usuarios = usuarios;
        this.contato = contato;
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
        //TODO: Ver como vai ficar essa imagem do usuario.
        //imgUsuario.setText();

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contato == true){
                    Intent it = new Intent(context,InformaçõesUsuarioActivity.class);
                    //TODO: PASSAR OBJETO PARA A OUTRA ACTIVITY
                    context.startActivity(it);
                }
                else{
                    Intent it = new Intent(context,InformaçõesUsuarioActivity.class);
                    //TODO: PASSAR OBJETO PARA A OUTRA ACTIVITY
                    context.startActivity(it);
                }
            }
        });

        return convertView;
    }

}
