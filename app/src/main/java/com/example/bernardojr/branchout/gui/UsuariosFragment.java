package com.example.bernardojr.branchout.gui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bernardojr.branchout.R;

/**
 * Created by romero on 19/01/17.
 */

public class UsuariosFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.usuarios_fragment,container,false);
        return view;
    }
}
