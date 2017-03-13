package com.example.bernardojr.branchout.gui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bernardojr.branchout.R;
import com.example.bernardojr.branchout.dominio.Usuario;

import java.util.List;

/**
 * Created by romero on 19/01/17.
 */

public class UsuariosFragment extends Fragment{

    private Context context;
    private ImageView imgRefresh;
    private LocationManager locationManager;
    private Location currentPosition;
    private ListView listView;
    private UsuariosAdapter usuariosAdapter;
    private List<Usuario> usuarios;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_usuarios,container,false);
        imgRefresh = (ImageView) view.findViewById(R.id.fragment_acitivty_img_refresh);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        listView = (ListView) view.findViewById(R.id.fragment_acitivty_list_usuarios);

        //TODO PEGAR USUARIOS PROXIMOS E COLOCA-LOS NA LISTAGEM
        //usuariosAdapter = new UsuariosAdapter(getActivity(),usuarios,false);
        //listView.setAdapter(usuariosAdapter);

        imgRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleNetworkUpdates();
            }
        });
        return view;
    }



    private boolean checkLocation() {
        if(!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

    private final LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            final double longitudeNetwork = location.getLongitude();
            final double latitudeNetwork = location.getLatitude();

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Toast.makeText(getActivity(), ""+latitudeNetwork + " "+ longitudeNetwork, Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    public void toggleNetworkUpdates() {
        if(!checkLocation())
            return;

            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 60 * 1000, 10, locationListenerNetwork);
            Toast.makeText(getActivity(), "Network provider started running", Toast.LENGTH_LONG).show();

    }





    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }
}
