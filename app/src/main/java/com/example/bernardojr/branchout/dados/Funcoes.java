package com.example.bernardojr.branchout.dados;

import android.text.TextUtils;
import android.util.Log;

import com.example.bernardojr.branchout.dominio.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;


public class Funcoes {

    private static final String LOG_TAG = Funcoes.class.getName();

    private Funcoes(){}

    public static ArrayList<Usuario> fetchUsersData(String requestURL){

        URL url = createUrl(requestURL);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpGETRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        return extractUsers(jsonResponse);
    }

    public static String getStringResponse(String requestURL)
    {
        URL url = createUrl(requestURL);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpGETRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        return jsonResponse;
    }

    private static ArrayList<Usuario> extractUsers(String usersJSON) {

        if(TextUtils.isEmpty(usersJSON))
            return null;

        ArrayList<Usuario> users = new ArrayList<>();

        try {

            JSONArray usersArray = new JSONArray(usersJSON);

            for (int i=0; i < usersArray.length(); i++){
                JSONObject userJson = usersArray.getJSONObject(i);
                String login = userJson.getString("login");
                String senha = userJson.getString("senha");
//                users.add(new Usuario(login, senha,));
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        return users;
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