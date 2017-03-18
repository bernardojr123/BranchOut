package com.example.bernardojr.branchout.dados;

import android.text.TextUtils;
import android.util.Log;

import com.example.bernardojr.branchout.dominio.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Funcoes {

    private static final String LOG_TAG = Funcoes.class.getName();

    private Funcoes(){}

    public static ArrayList<Usuario> fetchAllUsersData(String requestURL){

        URL url = createUrl(requestURL);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpGETRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        return extractAllUsers(jsonResponse);
    }

    public static Usuario fetchUserData(String requestURL){

        URL url = createUrl(requestURL);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpGETRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        return extractUsers(jsonResponse);
    }

    public static String getStringResponse(String requestURL, HashMap<String, String> params)
    {
        URL url = createUrl(requestURL);

        String jsonResponse = null;
        try {
//            jsonResponse = makeHttpGETRequest(url);
            jsonResponse = (params == null) ? makeHttpGETRequest(url) : makeHttpPOSTRequest(url, params);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        return jsonResponse;
    }

    private static ArrayList<Usuario> extractAllUsers(String usersJSON) {

        if(TextUtils.isEmpty(usersJSON))
            return null;

        ArrayList<Usuario> users = new ArrayList<>();

        try {

            JSONArray usersArrayJson = new JSONArray(usersJSON);

                    for (int j = 0; j < usersArrayJson.length(); j++) {
                        JSONObject userJson = usersArrayJson.getJSONObject(j);

                        String id = userJson.getString("id");
                        String email = userJson.getString("email");
                        String senha = userJson.getString("senha");
                        String nome = userJson.getString("nome");
                        String datanasc = userJson.getString("datanasc");
                        String descricao = userJson.getString("descricao");
                        String meiosdecontato = userJson.getString("meiosdecontato");
                        String idiomas = userJson.getString("idiomas");
                        String imagem = userJson.getString("imagem");
                        String x = userJson.getString("x");
                        String y = userJson.getString("y");
                        String ultimaLocalizacao = userJson.getString("ultimaLocalizacao");
                        users.add(new Usuario(id, imagem, nome, senha, email, datanasc,
                                descricao, meiosdecontato, idiomas,x, y, ultimaLocalizacao, null, null));
                    }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the user JSON results", e);
        }

        return users;
    }


    private static Usuario extractUsers(String usersJSON) {

        if(TextUtils.isEmpty(usersJSON))
            return null;

        Usuario usuario = null;

        try {

            JSONArray usersArrayJson = new JSONArray(usersJSON);
            JSONObject userJsonn =  usersArrayJson.getJSONObject(0);

//            usersArray.getJSONArray(1);
//            usersArray.getJSONArray(2);

            ArrayList<ArrayList<Usuario>> users = new ArrayList<>();

            for (int i=1; i < usersArrayJson.length(); i++){

                if(usersArrayJson.optJSONArray(i) != null) {

                    JSONArray jsonArray = usersArrayJson.getJSONArray(i);

                    ArrayList<Usuario> usersArrayList = new ArrayList<>();

                    for (int j = 0; j < jsonArray.length(); j++) {
                        JSONObject userJson = jsonArray.getJSONObject(j);

                        String id = userJson.getString("id");
                        String email = userJson.getString("email");
                        String senha = userJson.getString("senha");
                        String nome = userJson.getString("nome");
                        String datanasc = userJson.getString("datanasc");
                        String descricao = userJson.getString("descricao");
                        String meiosdecontato = userJson.getString("meiosdecontato");
                        String idiomas = userJson.getString("idiomas");
                        String imagem = userJson.getString("imagem");
                        String x = userJson.getString("x");
                        String y = userJson.getString("y");
                        String ultimaLocalizacao = userJson.getString("ultimaLocalizacao");
                        usersArrayList.add(new Usuario(id, imagem, nome, senha, email, datanasc,
                                descricao, meiosdecontato, idiomas,x, y, ultimaLocalizacao, null, null));
                    }
                    users.add(usersArrayList);
                }
                else
                    users.add(null);
            }
            usuario = new Usuario(userJsonn.getString("id"), userJsonn.getString("imagem"),
                    userJsonn.getString("nome"), userJsonn.getString("senha"),
                    userJsonn.getString("email"),userJsonn.getString("datanasc"),
                    userJsonn.getString("descricao"), userJsonn.getString("meiosdecontato"),
                    userJsonn.getString("idiomas"), userJsonn.getString("x"), userJsonn.getString("y"),
                    userJsonn.getString("ultimaLocalizacao"), users.get(0), users.get(1));

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the user JSON results", e);
        }

        return usuario;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;

        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL", e);
        }
        return url;
    }

    private static String makeHttpGETRequest(URL url) throws IOException {
        String jsonResponse = "";

        if(url == null)
            return jsonResponse;

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try{
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + httpURLConnection.getResponseCode());
            }
        }catch(IOException e){
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        }finally{
            if(httpURLConnection != null)
                httpURLConnection.disconnect();
            else
                inputStream.close();
        }
        return jsonResponse;
    }

    private static String makeHttpPOSTRequest(URL url, HashMap params) throws IOException {
        String jsonResponse = "";

        if(url == null)
            return jsonResponse;

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try{
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("POST");

            OutputStream os = httpURLConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(params));

            writer.flush();
            writer.close();
            os.close();


            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + httpURLConnection.getResponseCode());
            }
        }catch(IOException e){
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        }finally{
            if(httpURLConnection != null)
                httpURLConnection.disconnect();
            else
                inputStream.close();
        }
        return jsonResponse;
    }


    private static String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while(line != null){
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }
}