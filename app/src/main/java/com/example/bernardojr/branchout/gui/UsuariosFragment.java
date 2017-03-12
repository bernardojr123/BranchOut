package com.example.bernardojr.branchout.gui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bernardojr.branchout.R;

/**
 * Created by romero on 19/01/17.
 */

public class UsuariosFragment extends Fragment {

    private Context context;
    private ImageView imgRefresh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_usuarios,container,false);
        imgRefresh = (ImageView) view.findViewById(R.id.fragment_acitivty_img_refresh);


        imgRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(context,"fazer algo aqui",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }
}
