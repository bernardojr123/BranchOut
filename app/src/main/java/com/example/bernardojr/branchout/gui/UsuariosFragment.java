package com.example.bernardojr.branchout.gui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.IntegerRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bernardojr.branchout.R;
import com.example.bernardojr.branchout.dados.Sessao;
import com.example.bernardojr.branchout.dados.UsuarioDAO;
import com.example.bernardojr.branchout.dominio.Usuario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by romero on 19/01/17.
 */

public class UsuariosFragment extends Fragment{

    private Context context;
    private ImageView imgRefresh;
    private LocationManager locationManager;
    private Location currentPosition;
    private static ListView listView;
    private UsuariosAdapter usuariosAdapter;
    private static ArrayList<Usuario> usuarios = new ArrayList<>();
    private UsuarioDAO usuarioDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_usuarios,container,false);
        usuarioDAO = new UsuarioDAO(getActivity());
        imgRefresh = (ImageView) view.findViewById(R.id.fragment_acitivty_img_refresh);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        listView = (ListView) view.findViewById(R.id.fragment_acitivty_list_usuarios);
        toggleNetworkUpdates();


        imgRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuarioDAO = new UsuarioDAO(getActivity());
                usuarioDAO.pegaUsuarios(Sessao.getInstancia().getUsuario().getId());

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
                    if(Sessao.getInstancia().getUsuario() != null){
                        String id = Sessao.getInstancia().getUsuario().getId();
                        usuarioDAO.enviaLocalizacao(id,""+longitudeNetwork,""+latitudeNetwork);
                        Toast.makeText(getActivity(), ""+longitudeNetwork+ " "+ latitudeNetwork , Toast.LENGTH_SHORT).show();
                    }
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
                    LocationManager.NETWORK_PROVIDER, 30 * 1000, 0, locationListenerNetwork);
            Toast.makeText(getActivity(), "Network provider started running", Toast.LENGTH_LONG).show();

    }





    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    public static void carregarUsuarios(ArrayList<Usuario> usus, Context context){
        UsuariosAdapter.getInstancia().setContext(context);
        UsuariosAdapter.getInstancia().setUsuarios(limparPorProximidade(usus));
        UsuariosAdapter.getInstancia().setContato(false);
        listView.setAdapter(UsuariosAdapter.getInstancia());
    }

    private  static ArrayList<Usuario> limparPorProximidade(ArrayList<Usuario> usuarios){
        ArrayList<Usuario> usuariosFiltrados = new ArrayList<>();
        if (usuarios.size() != 0){
            for (int i=0; i < usuarios.size(); i++){
                Usuario usuario1 = usuarios.get(i);
                if (usuario1.getId() != Sessao.getInstancia().getUsuario().getId()
                        && usuario1.getX()!= null &&usuario1.getY()!= null && usuario1.getUltimaLocalizacao() != null){
                    String x = usuario1.getX();
                    String y = usuario1.getY();
                    String hr = usuario1.getUltimaLocalizacao();
                    if(x != "null" && y != "null" && hr != "null"){
                        Date dataHora = stringToDate(hr);
                        if(calcularTempo(dataHora) && calcularDistancia(x,y)){
                            usuariosFiltrados.add(usuario1);
                        }

                    }

                }
            }
        }
        return usuariosFiltrados;
    }

    private static Date stringToDate(String hr){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date data = new Date();
        try {
             data = sdf.parse(hr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return data;
    }

    private static boolean calcularDistancia(String x, String y){
        Location localOutro = new Location("");
        Location localUsuario = new Location("");
        localOutro.setLatitude(Double.parseDouble(y));
        localOutro.setLongitude(Double.parseDouble(x));
        localUsuario.setLongitude(Double.parseDouble(Sessao.getInstancia().getUsuario().getX()));
        localUsuario.setLatitude(Double.parseDouble(Sessao.getInstancia().getUsuario().getY()));
        float distanceInMeters =  localUsuario.distanceTo(localOutro);
        if (distanceInMeters < 1500.0){
            return true;
        }
        return false;
    }

    private static boolean calcularTempo(Date dataOutro){
        Date dataOutroNova;

        Calendar cal = Calendar.getInstance(); // creates calendar
        Date agr = new Date();
        cal.setTime(dataOutro); // sets calendar time/date
        cal.add(Calendar.HOUR_OF_DAY, -3); // adds one hour
        dataOutroNova = cal.getTime();
        TimeUnit timeUnit = TimeUnit.MINUTES;
        if(getDateDiff(dataOutroNova,agr,timeUnit) < 30){
            return true;
        }

        return false;
    }

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MINUTES);
    }
}
