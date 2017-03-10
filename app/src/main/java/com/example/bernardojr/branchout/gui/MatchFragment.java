package com.example.bernardojr.branchout.gui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bernardojr.branchout.R;


public class MatchFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_perfil_alterar, container, false);
        View view = inflater.inflate(R.layout.fragment_match,container,false);
        return view;
    }


}
