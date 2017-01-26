package com.example.bernardojr.branchout.gui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.bernardojr.branchout.R;

/**
 * Created by romero on 19/01/17.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    private Context context;

    public FragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new PerfilFragment();
            case 1:
                return new UsuariosFragment();
            default:
                return new MatchFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return context.getString(R.string.fragment_perfil);
            case 1:
                return context.getString(R.string.fragment_usuarios);
            default:
                return context.getString(R.string.fragment_match);
        }
    }
}
