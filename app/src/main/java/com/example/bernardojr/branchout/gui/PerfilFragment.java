package com.example.bernardojr.branchout.gui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bernardojr.branchout.R;

/**
 * Created by romero on 19/01/17.
 */

public class PerfilFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_perfil, container, false);
        return rootView;
    }
}
