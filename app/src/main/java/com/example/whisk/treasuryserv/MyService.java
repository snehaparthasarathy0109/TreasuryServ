package com.example.whisk.treasuryserv;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import com.example.whisk.CommonPack.*;

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

/**
 * Created by whisk on 12/6/2017.
 */
public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        System.out.println("ONBIND EXECUTED");
        return new IMyAidlInterface.Stub(){
            @Override
            public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString){
                System.out.println("SOMETHING HAPPENED");
            }

            @Override
            public int[] monthlyCash(int year) {
                System.out.println("Mon call received");
                int[] result = new int[12];
                StringBuilder builder = new StringBuilder();
                String query = "http://api.treasury.io/cc7znvq/47d80ae900e04f2/sql/?q=SELECT \"open_mo\" FROM t1 WHERE \"year\" = "+year+" AND \"account\" = \"Total Operating Balance\" group by(\"month\")";
                URL url = null;
                try {
                    url = new URL(query);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    InputStream stream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                    String line = "";
                    while ((line = reader.readLine()) != null){
                        builder.append(line);
                    };
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(builder.toString());
                JSONArray outSideArray = null;
                try {
                    outSideArray = new JSONArray(builder.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i=0; i<12; i++){
                    JSONObject object = null;
                    try {
                        object = outSideArray.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        result[i] = object.getInt("open_mo");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return result;
            }

            @Override
            public int[] dailyCash(int day, int month, int year, int num){
                int[] result = new int[12];
                StringBuilder builder = new StringBuilder();
                String query = "http://api.treasury.io/cc7znvq/47d80ae900e04f2/sql/?q=SELECT \"open_mo\" FROM t1 WHERE \"year\" = "+year+" AND \"account\" = \"Total Operating Balance\" group by(\"month\")";
                URL url = null;
                try {
                    url = new URL(query);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    InputStream stream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                    String line = "";
                    while ((line = reader.readLine()) != null){
                        builder.append(line);
                    };
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(builder.toString());
                JSONArray outSideArray = null;
                try {
                    outSideArray = new JSONArray(builder.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i=0; i<12; i++){
                    JSONObject object = null;
                    try {
                        object = outSideArray.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        result[i] = object.getInt("open_mo");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return result;
            }

            @Override
            public int yearlyAvg(int cash) throws RemoteException {
                int result = 0;
                StringBuilder builder = new StringBuilder();
                String query = "http://api.treasury.io/cc7znvq/47d80ae900e04f2/sql/?q=SELECT avg(\"open_today\") FROM t1 WHERE \"year\" = "+cash;
                URL url = null;
                try {
                    url = new URL(query);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    InputStream stream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                    String line = "";
                    while ((line = reader.readLine()) != null){
                        builder.append(line);
                    };
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(builder.toString());
                JSONArray outSideArray = null;
                try {
                    outSideArray = new JSONArray(builder.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i=0; i<1; i++){
                    JSONObject object = null;
                    try {
                        object = outSideArray.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        result = (int) object.getInt("avg(\"open_today\")");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return (int) result;
            }
        };
    }
}
