package com.example.bernardojr.branchout.gui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.bernardojr.branchout.dominio.Usuario;

import java.util.List;

/**
 * Created by romero on 20/01/17.
 */

public class UsuariosAdapter extends ArrayAdapter<Usuario> {


    public UsuariosAdapter(Context context, List<Usuario> usuarios) {
        super(context, 0, usuarios);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null)
            listItemView = LayoutInflater.from(getContext()).inflate();

        return null;
    }
}
