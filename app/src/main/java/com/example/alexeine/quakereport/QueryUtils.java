package com.example.alexeine.quakereport;

/**
 * Created by Alexeine on 28-07-2017.
 */

import android.text.TextUtils;
import android.util.Log;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.SimpleTimeZone;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    /** Sample JSON response for a USGS query */

    private QueryUtils() {
    }

    /**
     * Return a list of {@link Data} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<Data> Fetch_Earthquake_Data(String url){
        URL u = createUrl(url);
        String jsonRes = null;
        try {
            jsonRes = makeHttpRequest(u);
        } catch (IOException e) {
            e.printStackTrace();

        }
ArrayList<Data> earthquakes = extractFromHttp(jsonRes);
        return earthquakes;
    }

    private static ArrayList<Data> extractFromHttp(String jsonRes) {
        if (TextUtils.isEmpty(jsonRes)){
            return null;
        }
        ArrayList<Data> eqs = new ArrayList<Data>();

        try {
            JSONObject root = new JSONObject(jsonRes);
       JSONArray features = root.getJSONArray("features");
            for (int i = 0 ;i<features.length();i++){
                JSONObject currentObj = features.getJSONObject(i);
                JSONObject properties = currentObj.getJSONObject("properties");

                double magnitude = properties.getDouble("mag");
                String location = properties.getString("place");
                long time = properties.getLong("time");
                String Url = properties.getString("url");
                long date = properties.getLong("time");
                Data Earthquakee = new Data(magnitude, location, date, time, Url);
                eqs.add(Earthquakee);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return eqs;
    }

    private static String makeHttpRequest(URL u) throws IOException {
        HttpURLConnection uc = null;
        InputStream is =null;
        String jsRes = "";
        if (u==null){
            return  jsRes;
        }
        try {
            uc = (HttpURLConnection) u.openConnection();
            uc.setRequestMethod("GET");
            uc.setReadTimeout(1000);
            uc.setConnectTimeout(1500);
            uc.connect();

            if (uc.getResponseCode()==200){
                is = uc.getInputStream();
                jsRes = readFromStream(is);
            }else{
                Log.e("Respond Code","Error respond code :"+uc.getResponseCode());
            }
        }catch (IOException e){
            e.printStackTrace();
            Log.e("Problem JSON ","Error retrieving Json results");
        }finally {
            if (uc !=null){
                uc.disconnect();
            }if (is!=null){
is.close();
            }
        }

return jsRes;

    }

    private static String readFromStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        if (is!=null){
            InputStreamReader isr =new InputStreamReader(is);
            BufferedReader bfr = new BufferedReader(isr);
            String line = bfr.readLine();
            while (line!=null){
                sb.append(line);
                line=bfr.readLine();
            }
        }
        return sb.toString();
    }

    private static URL createUrl(String url) {
        URL u1 = null;
        try {
            u1 = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return u1;
    }


}