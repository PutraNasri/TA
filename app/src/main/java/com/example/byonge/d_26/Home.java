package com.example.byonge.d_26;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class Home extends Activity {
    private TextView textsuhu;
    private TextView textlembab;
    private TextView textwater;
    private TextView textwatertank;
    private TextView textwaktu;
    private String JSON_STRING;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        textsuhu= (TextView) findViewById(R.id.suhu);
        textlembab= (TextView) findViewById(R.id.lembab);
        textwater= (TextView) findViewById(R.id.water);
        textwatertank= (TextView) findViewById(R.id.tank);
        textwaktu= (TextView) findViewById(R.id.waktu);
        getJSON();
    }
    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Home.this,"Mengambil Data","Loading...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showEmployee();
            }
            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(config.URL_GET_DATA);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
    private void showEmployee(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(config.TAG_JSON_ARRAY);
            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String suhu = jo.getString(config.TAG_SUHU);
                String lembab = jo.getString(config.TAG_LEMBAB);
                String water = jo.getString(config.TAG_WATER);
                String watertank = jo.getString(config.TAG_WATERTANK);
                String waktu = jo.getString(config.TAG_WAKTU);

                textsuhu.setText(suhu);
                textlembab.setText(lembab);
                textwater.setText(water);
                textwatertank.setText(watertank);
                textwaktu.setText(waktu);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
